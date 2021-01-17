package com.github.caijh.framework.core.processor;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import com.github.caijh.framework.core.annotation.EnableMessageConstants;
import com.github.caijh.framework.core.exception.MessageConstantsException;
import org.springframework.util.FileCopyUtils;

public class MessageConstantsProcessor extends AbstractProcessor {

    private TypeElement typeElement;

    private boolean enableMessageConstants = false;
    private String propertiesLocation;
    private String packageName;
    private String className;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        typeElement = processingEnv.getElementUtils().getTypeElement("com.github.caijh.framework.core.annotation.EnableMessageConstants");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(this.typeElement);
        for (Element element : elements) {
            EnableMessageConstants annotation = element.getAnnotation(EnableMessageConstants.class);
            if (annotation.enable()) {
                this.enableMessageConstants = true;
                this.packageName = annotation.packageName();
                this.className = annotation.className();
                this.propertiesLocation = annotation.propertiesLocation();
                break;
            }
        }
        if (roundEnv.processingOver() && this.enableMessageConstants) {
            String fullClassName = this.packageName + "." + this.className;
            List<File> propertiesFiles = getPropertiesFiles(this.propertiesLocation);

            Set<String> propertyKeys = new HashSet<>();

            for (File propertiesFile : propertiesFiles) {
                Properties properties = new Properties();
                try (FileReader fileReader = new FileReader(propertiesFile)) {
                    properties.load(fileReader);
                } catch (Exception e) {
                    throw new MessageConstantsException(e);
                }
                propertyKeys.addAll(properties.stringPropertyNames());
            }

            final String fullName = fullClassName.replace(".", "/");

            final byte[] bytes = new MessageConstantsCodeGenerator().genCode(fullName, propertyKeys);

            String fileName = "target/classes/" + fullName + ".class";

            try {
                final Path path = Paths.get(fileName);
                if (Files.notExists(path.getParent())) {
                    Files.createDirectories(path.getParent());
                }
                final File file = Files.createFile(path).toFile();
                FileCopyUtils.copy(bytes, file);
            } catch (Exception e) {
                throw new MessageConstantsException(e);
            }
        }

        return true;
    }

    private List<File> getPropertiesFiles(String folder) {

        ClassLoader classLoader = getClass().getClassLoader();

        URL resource = classLoader.getResource(folder);
        if (resource == null) {
            throw new MessageConstantsException("该路径下无Properties文件");
        }

        try (final Stream<Path> stream = Files.walk(Paths.get(resource.toURI()))) {
            // dun walk the root path, we will walk all the classes
            return stream
                    .filter(e -> e.endsWith(".properties"))
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new MessageConstantsException(e);
        }
    }

}

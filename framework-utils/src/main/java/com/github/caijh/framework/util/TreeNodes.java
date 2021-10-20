package com.github.caijh.framework.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * hold all tree node.
 *
 * @param <K> tree nod key
 * @param <V> tree node value
 */
public class TreeNodes<K, V extends TreeNode<K, V>> {

    private final List<V> allNodes;

    public TreeNodes(List<V> allNodes) {
        this.allNodes = allNodes;
    }

    public List<V> asTree() {
        Map<K, V> map = allNodes.stream().collect(Collectors.toMap(V::getId, e -> e));

        List<V> tree = new ArrayList<>();
        allNodes.forEach(e -> {
            V parent = map.get(e.getParentId());
            if (parent == null) {
                tree.add(e);
            } else {
                parent.getChildren().add(e);
            }
        });
        return tree;
    }

}

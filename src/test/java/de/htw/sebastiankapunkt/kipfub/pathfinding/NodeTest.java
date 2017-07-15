package de.htw.sebastiankapunkt.kipfub.pathfinding;

import de.htw.sebastiankapunkt.kipfub.model.Node;
import org.junit.Assert;
import org.junit.Test;

public class NodeTest {
    @Test
    public void equals() throws Exception {
        Node a = new Node(1, 1);
        Node b = new Node(1, 1);

        Assert.assertTrue(a.equals(b));
        Assert.assertEquals(a.hashCode(), b.hashCode());
    }

}
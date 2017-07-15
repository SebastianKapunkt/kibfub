package de.htw.sebastiankapunkt.kipfub.pathfinding;

import de.htw.sebastiankapunkt.kipfub.model.Brush;
import de.htw.sebastiankapunkt.kipfub.model.Node;
import org.junit.Assert;
import org.junit.Test;

public class PathMoverTest {
    @Test
    public void isNearNextNode() throws Exception {
        PathMover mover = new PathMover(null, null, null);
        Brush brush = new Brush(1, 1);
        brush.x = 527;
        brush.y = 497;

        Node node = new Node(32, 32);

        Assert.assertTrue(mover.isNearNextNode(brush, node));
    }
}
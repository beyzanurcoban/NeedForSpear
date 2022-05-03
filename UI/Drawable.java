package UI;
import domain.DomainObject;

import java.awt.*;
import java.io.IOException;

public interface Drawable {

	void draw(Graphics2D g2d, DomainObject domainObject, int w, int h) throws IOException;

}

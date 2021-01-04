package lo02.shapeup.partie;

public interface PartieElement {
	void accept(PartieElementVisitor visitor);
}

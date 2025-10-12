package start;
public class HalloWelt {
	private String name;

	public HalloWelt(String name) {
		this.name = (name == null || name.isBlank()) ? "Welt" : name.trim();
	}

	public String begruessung() {
		return "Hallo, " + this.name + "!";
	}
}
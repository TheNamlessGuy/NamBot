package nambot.main.IO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Helpers {
	static Path getFile(String filename) throws IOException {
		Path path = Paths.get("res", "saves", filename);
		Files.createDirectories(path.getParent());
		return path;
	}
}
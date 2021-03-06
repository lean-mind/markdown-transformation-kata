package integration;

import infrastructure.Factory;
import mdtransformer.MarkdownTransformer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TransformerShould {

    private final String sourceFile = "test-source.md";
    private final String destinationFile = "test-destination.md";

    @Test
    public void apply_transformations_and_save_in_the_destination_file() throws IOException {
        MarkdownTransformer transformer = Factory.MarkdownTransformer();
        writeToFile(sourceFile,"[some link](http://test)");

        transformer.turnLinksIntoFootnotes(sourceFile, destinationFile);

        List<String> result = readFile(destinationFile);
        assertThat(result.get(0)).matches("some link \\[\\^anchor_.*]");
        assertThat(result.get(1)).matches("\\[\\^anchor_.*]: http://test");
        assertThat(result.size()).isEqualTo(2);
    }

    @AfterEach
    public void clean(){
        deleteFiles();
    }

    private void deleteFiles() {
        new File(sourceFile).delete();
        new File(destinationFile).delete();
    }

    private void writeToFile(String filename, String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(content);
        writer.close();
    }

    private List<String> readFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        List<String> lines = reader.lines().collect(Collectors.toList());
        reader.close();
        return lines;
    }
}

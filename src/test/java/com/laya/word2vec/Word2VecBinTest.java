package com.laya.word2vec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.After;
import org.junit.Test;

import com.laya.word2vec.Searcher.UnknownWordException;
import com.laya.word2vec.util.Common;

/**
 * Tests converting the binary models into
 * {@link com.laya.word2vec.Word2VecModel}s.
 * 
 * @see com.laya.word2vec.Word2VecModel#fromBinFile(File)
 * @see com.laya.word2vec.Word2VecModel#fromBinFile(File,
 *      java.nio.ByteOrder)
 */
public class Word2VecBinTest {

  /**
   * Tests that the Word2VecModels created from a binary and text
   * representations are equivalent
   */
  @Test
  public void testRead()
      throws IOException, UnknownWordException {
    File binFile = Common.getResourceAsFile(
            this.getClass(),
            "/com/laya/word2vec/tokensModel.bin");
    Word2VecModel binModel = Word2VecModel.fromBinFile(binFile);

    File txtFile = Common.getResourceAsFile(
            this.getClass(),
            "/com/laya/word2vec/tokensModel.txt");
    Word2VecModel txtModel = Word2VecModel.fromTextFile(txtFile);

  }

  private Path tempFile = null;

  /**
   * Tests that a Word2VecModel round-trips through the bin format without changes
   */
  @Test
  public void testRoundTrip() throws IOException, UnknownWordException {
    final String filename = "word2vec.c.output.model.txt";
    final Word2VecModel model =
        Word2VecModel.fromTextFile(filename, Common.readResource(Word2VecTest.class, filename));

    tempFile = Files.createTempFile(
            String.format("%s-", Word2VecBinTest.class.getSimpleName()), ".bin");
    try (final OutputStream os = Files.newOutputStream(tempFile)) {
      model.toBinFile(os);
    }

    final Word2VecModel modelCopy = Word2VecModel.fromBinFile(tempFile.toFile());
  }

  @After
  public void cleanupTempFile() throws IOException {
    if(tempFile != null)
      Files.delete(tempFile);
  }




}

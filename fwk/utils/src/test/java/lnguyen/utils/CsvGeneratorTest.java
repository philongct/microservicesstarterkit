package lnguyen.utils;

import java.util.ArrayList;
import java.util.List;

import lnguyen.utils.tabular.CsvMatrix;
import lnguyen.utils.tabular.Matrix;
import lnguyen.utils.tabular.gen.DataGenerator;
import lnguyen.utils.tabular.gen.TabularContentGenerator;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Before;
import org.junit.Test;

public class CsvGeneratorTest {

    private char[] chars;

    @Before
    public void genChars() {
        List<Character> chars = new ArrayList<>();

        for (char c = 'a'; c <= 'z'; ++c) {
            chars.add(c);
        }

        for (char c = '0'; c <= '9'; ++c) {
            chars.add(c);
            chars.add(' ');
        }

        this.chars = new char[chars.size()];
        for (int i = 0; i < chars.size(); i++) {
            this.chars[i] = chars.get(i);
        }
    }

    @Test
    public void generateData() {
        Matrix csvMatrix = new CsvMatrix(",");
        TabularContentGenerator generator = new TabularContentGenerator(csvMatrix);
        generator
                //card number
                .addColumn(new DataGenerator<String>() {
                    boolean init = false;
                    @Override
                    public String nextValue() {
                        if (!init) {
                            init = true;
                            return "CARD NUMBER";
                        }

                        return RandomStringUtils.randomNumeric(16);
                    }
                })
                //Time
                .addColumn(new DataGenerator<String>() {
                    boolean init = false;
                    @Override
                    public String nextValue() {
                        if (!init) {
                            init = true;
                            return "DATETIME";
                        }

                        return ISODateTimeFormat.dateTimeNoMillis().print(System.currentTimeMillis());
                    }
                })
                // amount
                .addColumn(new DataGenerator<String>() {
                    boolean init = false;
                    @Override
                    public String nextValue() {
                        if (!init) {
                            init = true;
                            return "AMOUNT";
                        }

                        return Float.toString(RandomUtils.nextFloat()*100000.0f);
                    }
                })
                // Store name
                .addColumn(new DataGenerator<String>() {
                    boolean init = false;
                    @Override
                    public String nextValue() {
                        if (!init) {
                            init = true;
                            return "STORE NAME";
                        }

                        return WordUtils.capitalize(RandomStringUtils.random(20, chars).replaceAll("\\s+", " ").trim());
                    }
                })
                // purchase item
                .addColumn(new DataGenerator<String>() {
                    boolean init = false;
                    @Override
                    public String nextValue() {
                        if (!init) {
                            init = true;
                            return "PURCHASED";
                        }

                        return StringUtils.upperCase(RandomStringUtils.random(100, chars).replaceAll("\\s+", " ").trim());
                    }
                })
                // authorization code
                .addColumn(new DataGenerator<String>() {
                    boolean init = false;
                    @Override
                    public String nextValue() {
                        if (!init) {
                            init = true;
                            return "AUTH CODEE";
                        }

                        return RandomStringUtils.randomAlphanumeric(12);
                    }
                });

        for (int i = ERandomUtils.nextInt(1000, 500); i > 0; --i,generator.nextLine());
        System.out.print(csvMatrix.getContent());
    }
}

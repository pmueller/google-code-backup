package util;

import java.util.*;
import java.io.*;
import model.item.equipment.armor.Armor;

/*
 * In the save function, begin with SaverLoader.start("class_name") then just SaverLoader.push
 * everything that you want to be saved, including primatives, arrays of primatives, saveable objects,
 * and arraylists of saveable objects.
 * End with a SaverLoader.close();
 *
 *
 * Before loading, make sure that SaverLoader has the SaveableCommand added with the addCommand(c)
 * function.
 * See SaveableCommand file.
 * To load just pull those in the same order, and finish with an assertEnd() function call.
 *
 */
public class SaverLoader implements saveableFactoryAcceptor {

    private Scanner scan;
    private int line;
    private BufferedWriter writer;
    private HashMap<String, SaveableFactory> map;

    /**
     * Constructor: sets line to zero
     */
    public SaverLoader() {
        line = 0;
    }

    /**
     * Constructor: sets the scanner
     * @param s
     */
    public SaverLoader(Scanner s) {
        scan = s;
    }

    /**
     * Constructor: sets the buffered writer
     * @param w
     */
    public SaverLoader(BufferedWriter w) {
        writer = w;
    }

    /**
     * Sets the scanner
     * @param s
     */
    public void setScanner(Scanner s) {
        scan = s;
    }

    /**
     * Sets the buffered writer
     * @param w
     */
    public void setBufferedWriter(BufferedWriter w) {
        writer = w;
    }

    /**
     * Initializes the saveable factory
     */
    public void init() {
        SaveableFactory.init(this);
    }

    /**
     * Closes the buffered writer
     * @throws IOException
     */
    public void closeBufferedWriter() throws IOException {
        writer.close();
    }

    /**
     * Adds the saveable factory into the hashmap
     * @param name
     * @param com
     */
    public void addFactory(String name, SaveableFactory com) {
        if (map == null) {
            map = new HashMap<String, SaveableFactory>();
        }
        map.put(name, com);
        ;

    }

    /**
     * Adds the list of pairs of string-saveable factory
     * @param al
     */
    public void addFactory(ArrayList<Pair<String, SaveableFactory>> al) {
        Pair<String, SaveableFactory> p;
        Iterator<Pair<String, SaveableFactory>> iter = al.iterator();
        while (iter.hasNext()) {
            p = iter.next();
            addFactory(p.getLeft(), p.getRight());
        }
    }

    /**
     * Removes the factory for the specified name
     * @param name
     */
    public void removeFactory(String name) {
        map.remove(name);
    }

    /**
     * Writes the start tag for the specified name
     * @param name
     * @throws IOException
     */
    public void start(String name) throws IOException {
        writer.write("M& ");
        assert (name != null && name != "");
        writer.write(name + "\n");
        writer.flush();
    }

    /**
     * Closes the current tag
     * @throws IOException
     */
    public void close() throws IOException {
        writer.write("&m\n");
        writer.flush();
    }

    /**
     * Writes a boolean
     * @param b
     * @throws IOException
     */
    public void push(boolean b) throws IOException {
        writer.write("b " + b + "\n");
        writer.flush();
    }

    /**
     * Writes an int
     * @param i
     * @throws IOException
     */
    public void push(int i) throws IOException {
        writer.write("i " + i + "\n");
        writer.flush();
    }

    /**
     * Writes a double
     * @param d
     * @throws IOException
     */
    public void push(double d) throws IOException {
        writer.write("d " + d + "\n");
        writer.flush();
    }

    /**
     * Writes a float
     * @param f
     * @throws IOException
     */
    public void push(float f) throws IOException {
        writer.write("i " + f + "\n");
        writer.flush();
    }

    /**
     * Writes a string
     * @param s
     * @throws IOException
     */
    public void push(String s) throws IOException {
        writer.write("s « " + s + " »\n");
        writer.flush();
    }

    /**
     * Writes (recursively) the list of saveable items
     * @param alist
     * @throws IOException
     */
    public void push(ArrayList<? extends Saveable> alist) throws IOException {
        writer.write("startList&\n");
        Iterator<? extends Saveable> iter = alist.iterator();
        push(alist.size());
        while (iter.hasNext()) {
            iter.next().save(this, true);
        }
        writer.write("&endList\n");
        writer.flush();
    }

    /**
     * Writes an array of booleans
     * @param list
     * @throws IOException
     */
    public void push(boolean list[]) throws IOException {
        writer.write("startList&\n");
        push(list.length);
        for (int i = 0; i < list.length; i++) {
            push(list[i]);
        }
        writer.write("&endList\n");
        writer.flush();
    }

    /**
     * Writes an array of ints
     * @param list
     * @throws IOException
     */
    public void push(int list[]) throws IOException {
        writer.write("startList&\n");
        push(list.length);
        for (int i = 0; i < list.length; i++) {
            push(list[i]);
        }
        writer.write("&endList\n");
        writer.flush();
    }

    /**
     * Writes an array of floats
     * @param list
     * @throws IOException
     */
    public void push(float list[]) throws IOException {
        writer.write("startList&\n");
        push(list.length);
        for (int i = 0; i < list.length; i++) {
            push(list[i]);
        }
        writer.write("&endList\n");
        writer.flush();
    }

    /**
     * Writes an array of doubles
     * @param list
     * @throws IOException
     */
    public void push(double list[]) throws IOException {
        writer.write("startList&\n");
        push(list.length);
        for (int i = 0; i < list.length; i++) {
            push(list[i]);
        }
        writer.write("&endList\n");
        writer.flush();
    }

    /**
     * Writes an array of strings
     * @param list
     * @throws IOException
     */
    public void push(String list[]) throws IOException {
        writer.write("startList&\n");
        push(list.length);
        for (int i = 0; i < list.length; i++) {
            push(list[i]);
        }
        writer.write("&endList\n");
        writer.flush();
    }

    /**
     * Writes a Saveable object
     * @param save
     * @param notSuperClass
     * @throws IOException
     */
    public void push(Saveable save, boolean notSuperClass) throws IOException {
        if (save == null) {
            writer.write("M& null\n&m\n");
            writer.flush();
            return;
        }
        save.save(this, notSuperClass);
    }

    /**
     * Writes an array of Saveable objects
     * @param list
     * @throws IOException
     */
    public void push(Saveable[] list) throws IOException {
        writer.write("startList&\n");
        push(list.length);
        for (int i = 0; i < list.length; i++) {
            push(list[i], true);
        }
        writer.write("&endList\n");
    }

    /**
     * Parses a boolean
     * @return
     * @throws IOException
     */
    public boolean pullBool() throws IOException {
        assertTag("b");
        return scan.nextBoolean();
    }

    /**
     * Parses an int
     * @return
     * @throws IOException
     */
    public int pullInt() throws IOException {
        assertTag("i");
        return scan.nextInt();
    }

    /**
     * Parses a float
     * @return
     * @throws IOException
     */
    public float pullFloat() throws IOException {
        assertTag("f");
        return scan.nextFloat();
    }

    /**
     * Parses a double
     * @return
     * @throws IOException
     */
    public double pullDouble() throws IOException {
        assertTag("d");
        return scan.nextDouble();
    }

    /**
     * Parses a string
     * @return
     * @throws IOException
     */
    public String pullString() throws IOException {
        assertTag("s");
        assertTag("«");
        line--;
        StringBuilder sb = new StringBuilder();
        String s;
        boolean space = false;
        while (true) {
            s = scan.next();
            if (s.equals("»")) {
                break;
            }
            if (space) {
                sb.append(" ");
            }
            sb.append(s);

            space = true;
        }
        s = sb.toString();
        return sb.toString();
    }

    /**
     * Parses an array of booleans
     * @return
     * @throws IOException
     */
    public boolean[] pullBoolArray() throws IOException {
        assertTag("startList&");
        int n = pullInt();
        boolean[] result = new boolean[n];
        for (int i = 0; i < n; i++) {
            result[i] = pullBool();
        }
        assertTag("&endList");
        return result;
    }

    /**
     * Parses an array of ints
     * @return
     * @throws IOException
     */
    public int[] pullIntArray() throws IOException {
        assertTag("startList&");
        int n = pullInt();
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = pullInt();
        }
        assertTag("&endList");
        return result;

    }

    /**
     * Parses an array of floats
     * @return
     * @throws IOException
     */
    public float[] pullFloatArray() throws IOException {
        assertTag("startList&");
        int n = pullInt();
        float[] result = new float[n];
        for (int i = 0; i < n; i++) {
            result[i] = pullInt();
        }
        assertTag("&endList");
        return result;
    }

    /**
     * Parses an array of doubles
     * @return
     * @throws IOException
     */
    public double[] pullDoubleArray() throws IOException {
        assertTag("startList&");
        int n = pullInt();
        double[] result = new double[n];
        for (int i = 0; i < n; i++) {
            result[i] = pullDouble();
        }
        assertTag("&endList");
        return result;
    }

    /**
     * Parses an array of strings
     * @return
     * @throws IOException
     */
    public String[] pullStringArray() throws IOException {
        assertTag("startList&");
        int n = pullInt();
        String[] result = new String[n];
        for (int i = 0; i < n; i++) {
            result[i] = pullString();
        }
        assertTag("&endList");
        return result;
    }

    /**
     * Parses a Saveable object
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T extends Saveable> T pullSaveable() throws IOException {
        assertTag("M&");
        String s2 = scan.next();
        SaveableFactory sf = map.get(s2);

        if(sf == null)
              throw new IOException("Class tag("+s2+") not found in factory on line "+line);
        T result = null;
        if( sf != null){

            result = (T) sf.construct();
            if (result != null) {
                result.load(this, true);
            } else {
                assertTag("&m");
            }
        }

        return result;
    }

    /**
     * Parses an array of Saveable objects
     * @param <T>
     * @param t
     * @return
     * @throws IOException
     */
    public <T extends Saveable> T[] pullSaveableArray(T[] t) throws IOException {
        ArrayList<T> al = this.<T>pullList();
        return al.toArray(t);
    }

    /**
     * Parses a list of Saveable objects
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T extends Saveable> ArrayList<T> pullList() throws IOException {
        assertTag("startList&");
        ArrayList<T> array = new ArrayList<T>();
        int n = pullInt();
        for (int i = 0; i < n; i++) {
            array.add(this.<T>pullSaveable());
        }
        assertTag("&endList");
        return array;
    }

    /**
     * Unit test: asserts the end of a tag
     * @throws IOException
     */
    public void assertEnd() throws IOException {
        assertTag("&m");
    }

    /**
     * Unit test: asserts tag equality
     * @param s
     * @throws IOException
     */
    private void assertTag(String s) throws IOException {
        String s2;
        if(scan.hasNext())
            s2 = scan.next();
        else{
            s2 = "End of File";
        }
        line++;
        if (!s.equals(s2)) {

            IOException e = new IOException("Tag(" + s2 + ") found instead of (" + s + ") on line " + line);
            e.printStackTrace();
            System.out.println(e.fillInStackTrace());
            throw e;
        }
    }

    /**
     * Test program
     * @param args
     */
    public static void main(String args[]) {
        SaverLoader sl = new SaverLoader();
        try {
            BufferedWriter w = new BufferedWriter(new FileWriter("delete.temp"));
            sl.setBufferedWriter(w);
            sl.push(6);
            sl.push(4.4);
            sl.push("Testy");
            sl.push("This is a test");
            int[] m = new int[20];
            for (int i = 0; i < m.length; i++) {
                m[i] = i;
            }
            sl.push(m);
            SaveableFactory sf = new SaveableFactory() {

                public Armor construct() {
                    return new Armor(0);
                }
            };
            sl.addFactory("Armor", sf);
            Armor a = new Armor("helm", 5);
            Armor[] alist = new Armor[5];

            for (int i = 0; i < alist.length; i++) {
                alist[i] = new Armor("SubHelm", 1 + 5 * i);
            }
            sl.push(a, true);
            sl.push(alist);
            sl.push("Cur HP");
            sl.closeBufferedWriter();




            Scanner s = new Scanner(new File("delete.temp"));
            sl.setScanner(s);
            System.out.println(sl.pullInt());
            System.out.println(sl.pullDouble());
            System.out.println(sl.pullString());
            System.out.println(sl.pullString());
            int[] k = sl.pullIntArray();
            System.out.println("Array=>");
            for (int i = 0; i < k.length; i++) {
                System.out.println(k[i]);
            }
            Armor ar = sl.<Armor>pullSaveable();
            System.out.println(ar.getName());
            System.out.println(ar.getArmorBonus());
            ArrayList<Armor> al;
            al = sl.pullList();
            Iterator<Armor> iter = al.iterator();

            while (iter.hasNext()) {
                ar = iter.next();
                System.out.println("Armor:" + ar.getName() + " " + ar.getArmorBonus());
            }
            System.out.println(sl.pullString());





        } catch (IOException e) {
            System.out.println(e);
            System.out.println(e.getMessage());
            System.out.print(e.getStackTrace());
        }
    }
}

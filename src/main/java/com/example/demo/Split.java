package com.example.demo;

import java.io.*;
import java.util.Scanner;

public class Split {
  public static void main(String args[]) {
    if (args.length == 0) {
      System.out.println("Type -h for getting help on this splitter");
    } else if (args.length == 1) {
      // if the paramenter is -h, the system displays the syntax for using this
      // utility.
      if (args[0].equals("-h")) {
        System.out.println("----------------------------------------------------------------");
        System.out.println("For Splitting:");
        System.out.println("java Splitme -s <filepath> <Number of lines in split file>");
        System.out.println("----------------------------------------------------------------");
        System.out.println("For Joining The Splitted File:");
        System.out.println("java Splitme -j <Path To file.sp>");
        System.out.println("----------------------------------------------------------------");
      } // Displaying the error if parameter required are not given.
      else if (args[0].equals("-s") || args[0].equals("-j")) {
        System.out.println("Parameters missing, type -h for help.....");
      }
    } else if ((args.length == 3) && args[2].startsWith("-")) {

      System.out.println("A Negative(-) Value For The Split File Size Not Allowed.");
      System.out.println("Type \"Splitme -h\" for Help!");

    }
    // if the parameter is -s then splitting of file happens.
    else if (args[0].equals("-s")) {
      if (args.length == 3) {
        String FilePath = "";
        FilePath = args[1];
        File filename = new File(FilePath);
        int numOfLines=0;
       // long splitFileSize = 0, bytefileSize = 0;
        if (filename.exists()) {
          try {
           // bytefileSize = Long.parseLong(args[2]);
            // splitFileSize = Long.parseLong(args[2]);
           // splitFileSize = bytefileSize * 1024 * 1024;
            // System.out.println("ByteFileSize" + mBbytefileSize);
            numOfLines = Integer.parseInt(args[2]);
            
            try {
              // Reading file and getting no. of files to be generated
             // String inputfile = "C:/test/5m Sales Records.csv"; // Source File Name.
              //double nol = 10000.0; // No. of lines to be split and saved in each output file.
              //File file = new File(inputfile);
              try (Scanner scanner = new Scanner(filename)) {
                int count = 0;
                while (scanner.hasNextLine()) {
                  scanner.nextLine();
                  count++;
                }
                System.out.println("Lines in the file: " + count); // Displays no. of lines in the input file.
        
                double temp = (count / numOfLines);
                int temp1 = (int) temp;
                int nof = 0;
                if (temp1 == temp) {
                  nof = temp1;
                } else {
                  nof = temp1 + 1;
                }
                System.out.println("No. of files to be generated :" + nof); // Displays no. of files to be generated.
        
                // ---------------------------------------------------------------------------------------------------------
        
                // Actual splitting of file into smaller files
        
                splitFile(FilePath, numOfLines, nof);
              }
            } catch (Exception e) {
              System.err.println("Error: " + e.getMessage());
            }
            
            //Splitme spObj = new Splitme();
            //spObj.split(FilePath, (long) splitFileSize);
            //spObj = null;
          } catch (Exception e) {
            e.printStackTrace();
          }
        } else {
          System.out.println("File Not Found....");
        }
        filename = null;
        FilePath = null;
      } else {
        System.out.println("Parameters missing, type -h for help.....");
      }
    } // if the parameter is -j then joining of file happens.
    
    System.out.println();
    System.out.println();

    

  }

  private static void splitFile(String inputfile, int nol, int nof) throws FileNotFoundException, IOException {
    FileInputStream fstream = new FileInputStream(inputfile);
    DataInputStream in = new DataInputStream(fstream);

    BufferedReader br = new BufferedReader(new InputStreamReader(in));
    String strLine;

    for (int j = 1; j <= nof; j++) {
      FileWriter fstream1 = new FileWriter("C:/test/File" + j + ".csv"); // Destination File Location
      BufferedWriter out = new BufferedWriter(fstream1);
      for (int i = 1; i <= nol; i++) {
        strLine = br.readLine();
        if (strLine != null) {
          out.write(strLine);
          if (i != nol) {
            out.newLine();
          }
        }
      }
      out.close();
    }

    in.close();
  }

  public void join(String FilePath) {
    long leninfile = 0, leng = 0;
    int count = 1, data = 0;
    try {
        File filename = new File(FilePath);
        // RandomAccessFile outfile = new RandomAccessFile(filename,"rw");

        OutputStream outfile = new BufferedOutputStream(new FileOutputStream(filename));
        while (true) {
            filename = new File(FilePath + count + ".sp");
            if (filename.exists()) {
                // RandomAccessFile infile = new RandomAccessFile(filename,"r");
                InputStream infile = new BufferedInputStream(new FileInputStream(filename));
                data = infile.read();
                while (data != -1) {
                    outfile.write(data);
                    data = infile.read();
                }
                leng++;
                infile.close();
                count++;
            } else {
                break;
            }
        }
        outfile.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}

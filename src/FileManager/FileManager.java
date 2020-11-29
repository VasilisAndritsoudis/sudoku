package FileManager;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * A class for reading from and writing to files
 */
public class FileManager
{
    /**
     * Writes data to a file
     * @param filename String path to a file
     * @param data ArrayList<String> the data to be written on a file
     */
    static void write(String filename, ArrayList<String> data){
        BufferedWriter writer = null;

        try{
            writer = new BufferedWriter(new FileWriter(filename));

            for(String string : data)
                writer.write(string + '\n');

        }catch(IOException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                assert writer != null;
                writer.close();
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Reads data from a file
     * @param filename String path to a file
     * @return ArrayList<String> the data read from the file
     */
    static ArrayList<String> read(String filename){
        ArrayList<String> output = null;
        BufferedReader reader = null;

        File temp = new File(filename);
        if(!temp.exists())
            return null;

        try{
            reader = new BufferedReader(new FileReader(filename));
            output = new ArrayList<>();

            String tempRead = reader.readLine();

            while(tempRead != null){
                output.add(tempRead);
                tempRead = reader.readLine();
            }

            return output;

        }catch(IOException e){
            System.out.println(e.getMessage());
        }finally{
            try
            {
                assert reader != null;
                reader.close();
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
        }

        return null;
    }

    /**
     * Reads a sudoku game board from a file
     * @param filename String path to a file
     * @param id an id that shows which sudoku board to load
     * @return ArrayList<Integer> the read sudoku board
     */
    static ArrayList<Integer> readSudokuGameBoard(String filename, int id)
    {
        try{
            File text = new File(filename);

            Scanner scanner = new Scanner(text);

            if(id != 0){
                while(Integer.parseInt(String.valueOf(scanner.nextLine().split(" ")[0])) != id - 1);
                scanner.nextLine();
            }

            String line = scanner.nextLine();

            String[] stringBoard = line.split(" ");

            ArrayList<Integer> output = new ArrayList<>();
            for(int i = 1; i < stringBoard.length; i++)
                output.add(Integer.parseInt(stringBoard[i]));

            return output;

        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Reads the grey spots that correspond to a sudoku board
     * @param filename String path to a file
     * @param id an id that shows which sudoku board to load
     * @return HashSet<Integer> the read grey spots
     */
    static HashSet<Integer> readSudokuGreySpots(String filename, int id){
        try{
            File text = new File(filename);

            Scanner scanner = new Scanner(text);

            if(id != 0){
                while(Integer.parseInt(String.valueOf(scanner.nextLine().split(" ")[0])) != id - 1);
                scanner.nextLine();
            }

            scanner.nextLine();

            String line = scanner.nextLine();

            String[] indexes = line.split(" ");

            HashSet<Integer> output = new HashSet<>();
            for(int i = 1; i < indexes.length; i++)
                output.add(Integer.parseInt(indexes[i]));

            return output;

        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Reads the colored area objects from a file
     * @param filename String path to a file
     * @param id an id that shows which killer board to load
     * @return String that represents a list of colored area objects
     */
    static String readKillerColoredAreas(String filename, int id)
    {
        try{
            File text = new File(filename);

            Scanner scanner = new Scanner(text);

            // Skips the sudoku game boards and sudoku grey spots
            if(id > 9){
                for(int i = 0; i < 20; i++)
                    scanner.nextLine();
                id -= 10;
            }

            if(id != 0)
                while(Integer.parseInt(String.valueOf(scanner.nextLine().split(" ")[0])) != id - 1);

            String line = scanner.nextLine();

            String[] splitLine = line.split(" ");
            StringBuilder actualLine = new StringBuilder();

            for(int i = 1; i < splitLine.length; i++)
                actualLine.append(splitLine[i]).append(" ");

            return actualLine.toString();

        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }

        return null;
    }
}

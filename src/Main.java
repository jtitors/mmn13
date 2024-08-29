import LinkedList.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static LinkedList.List.makeHeap;

public class Main {
    enum input {FILE_INPUT, USER_INPUT}
    private static Scanner sc = new Scanner(System.in);
    private static input inputMethod;
    public static void main(String[] args) {
        initSession();
    }

    public static void initSession(){
        System.out.println("Would you like to implement a mergeable heap with sorted, or unsorted lists?\n\n");
        System.out.println("1. Sorted Lists");
        System.out.println("2. Unsorted Lists");
        System.out.println("3. Exit");
        System.out.println();
        String selection = sc.nextLine();
        handleTypeSelection(selection);
    }

    private static void handleTypeSelection(String selection){
        if (selection.startsWith("1") || selection.toLowerCase().startsWith("s")) {
            // Sorted list
            List.setSort(true);
        } else if (selection.startsWith("2") || selection.toLowerCase().startsWith("u")) {
            // Unsorted list
            List.setSort(false);
        } else if (selection.startsWith("3") || selection.toLowerCase().startsWith("e") || selection.toLowerCase().startsWith("x")) {
            System.exit(0);
        } else {
            System.out.println("Invalid selection\n\n");
            initSession();
            return;
        }
        handleInput();
    }

    private static void handleInput() {
        String[] inputA = {};
        String[] inputB = {};

        System.out.println("\nWould you like to initialize from file or user input?");
        System.out.println("1. Initialize from file");
        System.out.println("2. Initialize from input");
        System.out.println("3. Exit");
        System.out.println();
        String selection = sc.nextLine();
        if (selection.startsWith("1") || selection.toLowerCase().startsWith("f")) {
            inputMethod = input.FILE_INPUT;
            System.out.println("List A:");
            inputA = readFromFile();
            System.out.println("List B:");
           inputB = readFromFile();
        } else if (selection.startsWith("2") || selection.toLowerCase().startsWith("i")) {
            inputMethod = input.USER_INPUT;
            System.out.println("\nList A:");
            inputA = readFromInput();
            System.out.println("\nList B:");
            inputB = readFromInput();

        } else if (selection.startsWith("3") || selection.toLowerCase().startsWith("e") || selection.toLowerCase().startsWith("x")) {
            System.exit(0);
        } else {
            System.out.println("Invalid selection\n\n");
            handleInput();
            return;
        }

        heapActions(new List(inputA), new List(inputB));

    }
    private static String[] readFromInput(){
        System.out.println("Enter the desired elements separated by a comma (',')");
        return sc.nextLine().split(",");
        }

    private static String[] readFromFile(){
        System.out.println("The file must contain numbers separated by a comma(','). Only the first line of the file will be used. \nEnter the file name inside input_files or the full path to the desired file.");
        String path = sc.nextLine();
        try {
            if (!path.contains("/") && !path.contains("\\")) {path = "input_files/" + path;}
            File file = new File(path);
            path = file.getAbsolutePath();
            Scanner fileScanner = new Scanner(file);
            return fileScanner.nextLine().split(",");
        } catch (FileNotFoundException e) {
            System.out.println("\""+path+"\"  -  File not found\n\n");
            return readFromFile();
        }
    }

    private static void heapActions(List listA, List listB){
        System.out.println("\nHeap actions:");
        System.out.println("1. Merge");
        System.out.println("2. Get Minimum");
        System.out.println("3. Print lists");
        System.out.println("4. Remake heap");
        System.out.println("5. Exit");
        System.out.println();
        String selection = sc.nextLine();
        if (selection.startsWith("1") || selection.toLowerCase().startsWith("me")) {
            callMerge(listA, listB);
            return;
        }
        if (selection.startsWith("2") || selection.toLowerCase().startsWith("mi")) {
            System.out.println("Minimum: "+selectActionTarget(listA, listB).Minimum());
            heapActions(listA, listB);
            return;
        }
        if (selection.startsWith("3") || selection.toLowerCase().startsWith("p")){
            printList(selectActionTarget(listA, listB));
            heapActions(listA, listB);
            return;
        }
        if (selection.startsWith("4") || selection.toLowerCase().startsWith("h")){
            List selected = selectActionTarget(listA, listB);
            if (inputMethod == input.FILE_INPUT) makeHeap(selected, readFromFile());
            else makeHeap(selected, readFromInput());
            printList(selected);
            heapActions(listA, listB);
            return;
        }

        if (selection.startsWith("5") || selection.toLowerCase().startsWith("e") || selection.toLowerCase().startsWith("x")){
            System.exit(0);
        }
        System.out.println("Invalid selection\n\n");
        heapActions(listA, listB);

    }

    private static void callMerge(List listA, List listB){
        System.out.println("\nList B is being merged into list A.");
        List.union(listA, listB);
        System.out.println("List A:");
        printList(listA);
        System.out.println("List B:");
        printList(listB);
        heapActions(listA, listB);
    }

    private static void printList(List list){
        System.out.println(list);
    }


    private static List selectActionTarget(List listA, List listB){
        System.out.println("\nWhich list would you like to perform the action on?");
        System.out.println("1. List A");
        System.out.println("2. List B");
        System.out.println("3. Cancel operation");
        System.out.println();
        String selection = sc.nextLine();
        if (selection.startsWith("1") || selection.toLowerCase().startsWith("a")) {
            System.out.println("\nSelected List A");
            return listA;
        }
        if (selection.startsWith("2") || selection.toLowerCase().startsWith("b")) {
            System.out.println("\nSelected List B");
            return listB;
        }
        if (selection.startsWith("3") || selection.toLowerCase().startsWith("c")) {
            heapActions(listA, listB);
            return null;
        }
        else {
            System.out.println("Invalid selection\n\n");
            return null;
        }
    }
}
package com.sononio.bostongene.translate;

import com.sononio.bostongene.translate.translator.Translator;
import com.sononio.bostongene.translate.translator.TranslatorException;

import java.util.Scanner;

/**
 * Class with main function
 */
public class Main {
    public static void main(String[] args) {
        Translator translator = new Translator();
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("Your phrase in english:");
            try {
                System.out.println("In russian:\n" + translator.translate(scan.nextLine(), "en"));
            } catch (TranslatorException e) {
                System.out.println("An error has occurred: " + e.getMessage());
            }
            System.out.println();
        }
    }
}

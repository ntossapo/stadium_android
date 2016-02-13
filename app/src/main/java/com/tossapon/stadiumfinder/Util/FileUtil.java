package com.tossapon.stadiumfinder.Util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by benvo_000 on 12/2/2559.
 */
public class FileUtil {
    public static void writeFile(Context context, String fileName, String data) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.getApplicationContext().openFileOutput(fileName, context.MODE_PRIVATE));
        outputStreamWriter.write(data);
        outputStreamWriter.close();
    }

    public static String readFile(Context context, String fileName) throws IOException {
        String text;
        InputStreamReader fileReader = new InputStreamReader(context.openFileInput(fileName));
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String receivingString = "";
        StringBuilder builder = new StringBuilder();
        while ((receivingString = bufferedReader.readLine()) != null)
            builder.append(receivingString);
        fileReader.close();
        text = builder.toString();
        return text;
    }
}

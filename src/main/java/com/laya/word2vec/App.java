package com.laya.word2vec;

import java.io.*;

public class App {

    public static void main(String[] args){

        //serialize();
        SerializableWord2Vec model = deserialize();
        System.out.println("Model loaded with " + model.vocab.size());
        System.out.println("Vector for word : " + model.vocab.get(4234));
        System.out.println("is : " + model.rawVectors.get(model.vocab.get(4234)));

    }

    private static void serialize(){
        System.out.println("Loading Model..........");

        File gModel = new File("/Users/younesdidi/Documents/Macchit/Data/w2vecModels/glove.6B.50d.txt");
        Word2VecModel word2vec = null;
        try {
            word2vec = Word2VecModel.fromTextFile(gModel);
            SerializableWord2Vec myModel = new SerializableWord2Vec(word2vec);

            FileOutputStream fileOut = new FileOutputStream("/tmp/employee.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(myModel);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /tmp/employee.ser");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static SerializableWord2Vec deserialize(){
        SerializableWord2Vec loadedModel = null;
        try {
            FileInputStream fileIn = new FileInputStream("/tmp/employee.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            loadedModel = (SerializableWord2Vec) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }
        return loadedModel;
    }
}

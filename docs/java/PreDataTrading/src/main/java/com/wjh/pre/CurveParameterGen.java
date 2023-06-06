package com.wjh.pre;

import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;

import java.io.*;

public class CurveParameterGen {
    public static int rbit = 224; // Zp
    public static int qbit = 224; // G

    public static void main(String[] args)  {
        TypeACurveGenerator pg = new TypeACurveGenerator(rbit,qbit);
        PairingParameters typeAparam = pg.generate();
        System.out.println("parameter of curve\n:" + typeAparam.toString());

        BufferedWriter out;
        try {
            out = new BufferedWriter(new FileWriter("a.properties"));
            System.out.println(("write to file"));
            out.write(typeAparam.toString());
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

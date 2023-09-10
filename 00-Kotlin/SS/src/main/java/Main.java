import java.math.BigInteger;
import java.nio.Buffer;
import java.util.function.BiFunction;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        int mod = 9;

        Paillier p = new Paillier(mod);

        //Mensaje que voy a encriptar

        BigInteger m = new BigInteger("1");
        BigInteger m1 = new BigInteger("2");

        //Cifrar

        BigInteger me = p.encrypt(m);
        BigInteger me1 = p.encrypt(m1);


        //Descifrar

        BigInteger md = p.decrypt(me);

        System.out.println("Mensaje: "+ m.toString() );
        System.out.println("Mensaje Encriptado: "+ me );


        System.out.println("Mensaje Desencriptado: "+ md );
        System.out.println("----------------------------------------" );
        propiedadSuma(  1024, m,m1);
        System.out.println("----------------------------------------" );
        multiplicacion(1024, m,m1);





    }
    public static int propiedadSuma (int mod, BigInteger m, BigInteger m1) throws Exception {
        Paillier p = new Paillier(mod);
        BigInteger me = p.encrypt(m);
        BigInteger me1 = p.encrypt(m1);
        BigInteger multiplicacion = me.multiply(me1);
        BigInteger md = p.decrypt(modulo(multiplicacion, p.getN()));
        System.out.println("Suma parte 1 = " + md);
        BigInteger suma = m.add(m1);
        BigInteger mod2 = suma.mod(p.getN());
        System.out.println("Suma parte 2 = " + mod2);
        return 0;
    }
    public static BigInteger modulo (BigInteger n1, BigInteger n2){
        BigInteger potencia = n2.pow(2);
        BigInteger modulo = n1.mod(potencia);

        return modulo;
    }
    public static void multiplicacion (int mod, BigInteger m, BigInteger m1) throws Exception {
        Paillier p = new Paillier(mod);
        BigInteger me = p.encrypt(m);
        BigInteger potencia = me.pow(m1.intValue());
        BigInteger desencriptar = potencia.mod(p.getN().pow(2));
        BigInteger md = p.decrypt(desencriptar);
        System.out.println("multiplicacion parte 1 = "+ md);

        //-----------------------------
        BigInteger mult = m.multiply(m1);
        BigInteger multprespueta = mult.mod(p.getN());
        System.out.println("multiplicacion parte 2 = "+ multprespueta);


    }
}
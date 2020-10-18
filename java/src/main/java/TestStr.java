public class TestStr {
    public static void main(String[] args) {
        test1();

    }

    public static void test1(){
        String a=new  String("abc");
        a.intern();
        String b="abc";
        System.out.println(b==a.intern());
        System.out.println(a==b);
    }
    public static void test2(){
        String a=new  String("abc")+new   String("d");
        a.intern();
        //System.out.println(a==a.intern());
        String b="abcd";
        //System.out.println(b==b.intern());
        System.out.println(a==b);
    }

}

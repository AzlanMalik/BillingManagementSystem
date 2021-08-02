package Application.Utils;

public class testing1
{
    public static void main(String[] args){
        testImplementation test1 = new testImplementation();
        String[] arr = test1.testConnection(1);
        System.out.println(arr[0]);
        System.out.println();
        String[] arr1 = test1.testConnection(1);
        System.out.println(arr1[0]);
    }
}

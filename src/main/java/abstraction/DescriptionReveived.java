package abstraction;

public class DescriptionReveived{
    public DescriptionReveived(byte[] arr, String URL){
        this.message=arr;
        this.URLSender=URL;
    }
    public byte[] message;
    public String URLSender;
}
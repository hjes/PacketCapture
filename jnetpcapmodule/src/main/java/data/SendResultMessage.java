package data;

import java.util.Date;

public class SendResultMessage {

    public static int SEND_RESULT_OK = 0,
                      SEND_RESULT_ERROR = 1;

    private int resultCode;
    private String interfaceName;
    private String errorMessage;
    private Date messageTime;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public Date getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Date messageTime) {
        this.messageTime = messageTime;
    }

    public SendResultMessage(int resultCode, String interfaceName,Date messageTime) {
        this.resultCode = resultCode;
        this.interfaceName = interfaceName;
        this.messageTime = messageTime;
    }

    public static SendResultMessage IS_OK(String interfaceName,Date messageTime){
        return new SendResultMessage(SEND_RESULT_OK,interfaceName,messageTime);
    }

    public static SendResultMessage IS_ERROR(String interfaceName,Date messageTime,String errorMessage){
        SendResultMessage sendResultMessage = new SendResultMessage(SEND_RESULT_ERROR,interfaceName,messageTime);
        sendResultMessage.setErrorMessage(errorMessage);
        return sendResultMessage;
    }
}

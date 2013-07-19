package actions;

import dao.MessageDao;
import java.util.ArrayList;
import java.util.Date;
import tables.Userinfo;

public class Message extends BaseAction {

    public static class GetMessageParam {

        int least;
    }

    public String getMessage() {
        GetMessageParam param = (GetMessageParam) getParam(GetMessageParam.class);
        MessageDao md = new MessageDao();
        Userinfo ui = (Userinfo) session("myUserinfo");
        ArrayList<tables.Message> mdList = md.findMessagebyuid(ui.getUid(), param.least);
        return jsonResult(mdList);
    }

    public static class DeleteMessageByIdParam {

        int messageId;
    }

    public String deleteMessageById() {
        DeleteMessageByIdParam param = (DeleteMessageByIdParam) getParam(DeleteMessageByIdParam.class);
        MessageDao md = new MessageDao();
        tables.Message m = md.findMessagebyid(param.messageId);
        if (m == null) {
            return jsonResult("messageId");
        }
        md.deleteMessage(m);
        return jsonResult("ok");
    }

    public static class SendMessageParam {

        tables.Message message;
    }

    public String sendMessage() {
        SendMessageParam param = (SendMessageParam) getParam(SendMessageParam.class);
        if (param.message == null) {
            return jsonResult("message");
        }

        Userinfo ui = (Userinfo) session("myUserinfo");
        param.message.setAuthorId(ui.getUid());
        if (param.message.getTargetId() == null || param.message.getTargetId().equals("")) {
            return jsonResult("targetId");
        }
        if (param.message.getMessContent() == null || param.message.getMessContent().equals("")) {
            return jsonResult("messContent");
        }
        param.message.setMessDate(new Date());
        param.message.setMessageId(null);
        MessageDao md = new MessageDao();
        md.addMessage(param.message);
        return jsonResult("ok");
    }
}

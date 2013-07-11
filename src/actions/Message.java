package actions;

import java.util.ArrayList;
import java.util.Date;

import tables.Userinfo;

import com.sun.mail.handlers.message_rfc822;

import dao.MessageDao;

import actions.User.LoginParam;

public class Message extends BaseAction{
	public static class GetMessageByUserIdParam{
		int userId;
	}
	
	public String getMessageByUserId(){
		GetMessageByUserIdParam param = (GetMessageByUserIdParam) getParam(GetMessageByUserIdParam.class);
		MessageDao md = new MessageDao();
		ArrayList<tables.Message> mdList = md.findMessagebyuid(param.userId);
		return jsonResult(mdList);
	}
	
	
	public static class DeleteMessageByIdParam{
		int messageId;
	}
	
	public String deleteMessageById(){
		DeleteMessageByIdParam param = (DeleteMessageByIdParam) getParam(DeleteMessageByIdParam.class);
		MessageDao md = new MessageDao();
		tables.Message m = md.findMessagebyid(param.messageId);
		if(m == null){
			return jsonResult("messageId");
		}
		md.deleteMessage(m);		
		return jsonResult("ok");
	}
	
	
	public static class SendMessageParam{
		tables.Message message;
	}
	
	public String sendMessage(){
		SendMessageParam param = (SendMessageParam) getParam(SendMessageParam.class);
		if (param.message == null){
			return jsonResult("message");
		}
		
		Userinfo ui = (Userinfo)session("myUserinfo");
		param.message.setAuthorId(ui.getUid());
		if(param.message.getTargetId() == null || param.message.getTargetId().equals("")){
			return jsonResult("targetId");
		}
		if(param.message.getMessContent() == null || param.message.getMessContent().equals("")){
			return jsonResult("messContent");
		}
		if(param.message.getMessDate() == null || param.message.getMessDate().equals("")){
			param.message.setMessDate(new Date());
		}
		param.message.setMessageId(null);
		MessageDao md = new MessageDao();
		md.addMessage(param.message);
		return jsonResult("ok");
	}
}

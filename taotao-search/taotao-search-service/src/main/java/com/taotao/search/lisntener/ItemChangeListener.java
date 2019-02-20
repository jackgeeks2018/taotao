package com.taotao.search.lisntener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.search.dao.SearchDao;

public class ItemChangeListener implements MessageListener {
	
	@Autowired
	private SearchDao searchservice;

	@Override
	public void onMessage(Message message) {
		try {
			TextMessage textMessage = null;
			Long itemId = null; 
			//取商品id
			if (message instanceof TextMessage) {
				textMessage = (TextMessage) message;
				itemId = Long.parseLong(textMessage.getText());
			}
			//向索引库添加文档
                 searchservice.updateItemById(itemId);						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

} 

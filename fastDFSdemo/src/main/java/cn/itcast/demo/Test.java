package cn.itcast.demo;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class Test {

	public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
		// 1.鍔犺浇閰嶇疆鏂囦欢
		ClientGlobal.init("D:\\java\\taotao\\fastDFSdemo\\src\\main\\resources\\fdfs_client.conf");
		// 2.鏋勫缓涓�涓鐞嗚�呭鎴风
		TrackerClient client=new TrackerClient();
		// 3.杩炴帴绠＄悊鑰呮湇鍔＄
		TrackerServer trackerServer = client.getConnection();
		//4. 澹版槑瀛樺偍鏈嶅姟绔�
		StorageServer storageServer=null;
		//5. 鑾峰彇瀛樺偍鏈嶅姟鍣ㄧ殑瀹㈡埛绔璞�
		StorageClient storageClient=new StorageClient(trackerServer, storageServer);
		//6.涓婁紶鏂囦欢
		String[] strings = storageClient.upload_file("C:\\Users\\h5848\\Desktop\\img\\1.jpg", "jpg", null);
		//7.鏄剧ず涓婁紶缁撴灉 file_id
		for(String str:strings){
			System.out.println(str);
		}		
		
	}

}

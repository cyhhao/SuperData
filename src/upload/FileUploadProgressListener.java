package upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;


public class FileUploadProgressListener implements ProgressListener {

	private HttpSession session;
	private long megaBytes = -1;

	
	public FileUploadProgressListener(HttpServletRequest request) {
		session = request.getSession();
		FileUploadStatus newUploadStatus = new FileUploadStatus();
		session.setAttribute("upladeStatus", newUploadStatus);
	}

	/**
	 * 
	 * 为了进度条监听器不会引起性能问题
	 * 解决方案,是减少进步条的活动数
	 * 比如，只有当上传�?兆字节的时�?才反馈给用户
	 * 
	 */
	public void update(long pBytesRead, long pContentLength, int pItems) {
		/*long mBytes = pBytesRead / 1048576;
		if (megaBytes == mBytes) {
			return;
		}
		megaBytes = mBytes;*/
		FileUploadStatus status = (FileUploadStatus) session.getAttribute("upladeStatus");
		if (pContentLength == -1) {
			status.setStatusMsg("已完" + pItems +"个文件的上传");
		}else {
			status.setStatusMsg("正在上传"+ pItems +"个文");
		}
		status.setError("0");
	    status.setReadedBytes(pBytesRead);
	    status.setTotalBytes(pContentLength);
	    status.setCurrentItem(pItems);
	}
	
}

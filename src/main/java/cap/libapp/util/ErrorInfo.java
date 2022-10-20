package cap.libapp.util;

import org.springframework.http.HttpStatus;

public class ErrorInfo 				//Error response.
{
	/* Error Response Entity for Library app.
	 * Author - Shrey.
	 */
	
		private int statusCode;
		private HttpStatus status;
		private String message;
		private String uri;
		private String httpMethod;
		
		public ErrorInfo(int statusCode, HttpStatus status, String message, String uri, String httpMethod) {
			super();
			this.statusCode = statusCode;
			this.status = status;
			this.message = message;
			this.uri = uri;
			this.httpMethod = httpMethod;
		}
		public String getUri() {
			return uri;
		}
		public void setUri(String uri) {
			this.uri = uri;
		}
		public int getStatusCode() {
			return statusCode;
		}
		public void setStatusCode(int statusCode) {
			this.statusCode = statusCode;
		}
		public HttpStatus getStatus() {
			return status;
		}
		public void setStatus(HttpStatus status) {
			this.status = status;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getHttpMethod() {
			return httpMethod;
		}
		public void setHttpMethod(String httpMethod) {
			this.httpMethod = httpMethod;
		}
}

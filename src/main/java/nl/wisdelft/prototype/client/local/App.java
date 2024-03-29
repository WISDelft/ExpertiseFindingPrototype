package nl.wisdelft.prototype.client.local;

import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ioc.client.api.EntryPoint;

import com.google.gwt.user.client.History;

/**
 * This is the entry point to the client portion of the web application. At
 * compile time, Errai finds the {@code @EntryPoint} annotation on this class
 * and generates bootstrap code that creates an instance of this class when the
 * page loads. This client-side bootstrap code will also call the
 * {@link #init()} method because it is annotated with the
 * {@code @PostConstruct} annotation.
 */
@EntryPoint
public class App {

	@AfterInitialization
	public void loadApp() {
		History.fireCurrentHistoryState();
	}
	// /**
	// * The Errai Data Sync helper class which allows us to initiate a data
	// * synchronization with the server.
	// */
	// @Inject
	// private ClientSyncManager syncManager;
	//
	// @PostConstruct
	// private void init() {
	// // This is specifying the relative path to the REST endpoint used to
	// store
	// // complaints on the server. When compiling the native mobile app of this
	// // demo, this needs to be changed to an absolute URL.
	// RestClient.setApplicationRoot("/errai-tutorial/rest");
	// //
	// RestClient.setApplicationRoot("http://10.15.16.207:8080/errai-tutorial/rest");
	// }
	//
	// /**
	// * Performs a full two-way data synchronization between the client and the
	// * server: the server gets all new and updated UserComplaint objects from
	// us,
	// * and we get all new and updated UserComplaint objects from the server.
	// */
	// public void sync() {
	// sync(new RemoteCallback<List<SyncResponse<UserComplaint>>>() {
	// @Override
	// public void callback(List<SyncResponse<UserComplaint>> response) {
	// LogUtil.log("Received sync response:" + response);
	// }
	// });
	// }
	//
	// /**
	// * Performs a full two-way data synchronization between the client and the
	// * server: the server gets all new and updated UserComplaint objects from
	// us,
	// * and we get all new and updated UserComplaint objects from the server.
	// *
	// * @param callback
	// * the callback to invoked upon completion of the data sync request.
	// */
	// public void sync(RemoteCallback<List<SyncResponse<UserComplaint>>>
	// callback) {
	// LogUtil.log("Sending sync:");
	// syncManager.coldSync("allComplaints", UserComplaint.class,
	// Collections.<String, Object> emptyMap(), callback, null);
	// }
}
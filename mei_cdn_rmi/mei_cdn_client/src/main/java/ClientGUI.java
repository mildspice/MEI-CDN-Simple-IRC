import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;


public class ClientGUI {

    // # GUI fields
    private static JFrame guiFrame; // main Frame
	private static JPanel chatBoardPanel, chatInputPanel;
	private static JTextField messageInput;
	private static Font msgFont = new Font("monospaced", Font.PLAIN, 12);
    private static Font listFont = new Font("Arial", Font.PLAIN, 14);
	private static Border blankBorder = BorderFactory.createEmptyBorder(10,10,20,10);
    private static JList<String> onlineUsers;
    private static DefaultListModel<String> listModel;
    private static JTextArea chatBoard;
    private static JButton privateMsgBtn, loginBtn, msgBtn, sendFileBtn;
    private static JPanel clientPanel, userPanel;

    // # RMI | IRC fields
    private static String user, message;
    private static AuthInterface serverAuthStub;
    private static ChatClient clientChatStub;

    private static ActionListener temp = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent click) {
            try {
                if (click.getSource() == loginBtn){
                    user = messageInput.getText();				
                    if (user.length() != 0){
                        guiFrame.setTitle("Hello " + user + "!");
                        messageInput.setText("");
                        chatBoard.append("SERVER> You are being connected to chat, hold a moment ...\n");

                        String cleanUsername = user.replaceAll("\\s+","_").replaceAll("\\W+","_");
                        clientChatStub = new ChatClient();
                        if (!serverAuthStub.login(cleanUsername, clientChatStub)) {
                            JOptionPane.showMessageDialog(guiFrame, "> Error logging in.\nPlease try again later or use a a different username", 
                                "Login failed", JOptionPane.ERROR_MESSAGE);
                        } else {
                            loginBtn.setEnabled(false);
                            msgBtn.setEnabled(true);
                            chatBoard.append("SERVER> Logged in successfuly! You may start chatting.\n");
                        }
                    } else{
                        JOptionPane.showMessageDialog(guiFrame, "> Username required!");
                    }

                } else if(click.getSource() == msgBtn){
                    message = messageInput.getText();
                    messageInput.setText("");
                    // TODO implement send a public chat message
                    // sendMessage(message); to server
                    System.out.println("> Chat message from " + user + ": " + message + "\n");

                } else if(click.getSource() == privateMsgBtn){
                    List<String> selectedUsers = onlineUsers.getSelectedValuesList();
                    // TODO implement send a private chat message
                    message = messageInput.getText();
                    messageInput.setText("");
                    // sendPrivateMessage(message, selectedUsers); to server
                }
            }
            catch (RemoteException remoteExc) {			
                remoteExc.printStackTrace();	
            }
	    }
    };

    public static void openChatMainWindow(AuthInterface authStub) {
        serverAuthStub = authStub;
        guiFrame = new JFrame("Simple IRC");

        guiFrame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        
		    	if(serverAuthStub != null && user != null && !user.isEmpty()) {
			    	try {
			        	serverAuthStub.logout(user);
					} catch (RemoteException e) {
						e.printStackTrace();
					}		        	
		        }
                super.windowClosing(windowEvent);
		    }   
		});
        guiFrame.setLocationByPlatform(true);
        guiFrame.setAlwaysOnTop(true);
		guiFrame.setLocation(150, 150);
        guiFrame.setPreferredSize(new Dimension(700, 450));

		JPanel chatBoardOuterPanel = new JPanel(new BorderLayout());
		chatBoardOuterPanel.add(setChatInputsPanel(), BorderLayout.PAGE_END);
		chatBoardOuterPanel.add(setChatBoardPanel(), BorderLayout.PAGE_START);
		
		guiFrame.setLayout(new BorderLayout());
		guiFrame.add(chatBoardOuterPanel, BorderLayout.CENTER);
		guiFrame.add(setOnlineUsersPanel(), BorderLayout.WEST);

        guiFrame.pack();
		messageInput.requestFocus();

        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setVisible(true);
    }

	public static JPanel setChatBoardPanel() {
		chatBoard = new JTextArea("SERVER> Welcome!\nSERVER> Enter a unique username below and press SIGN UP to begin.\n.\n.\n.\n", 14, 34);
		chatBoard.setMargin(new Insets(10, 10, 10, 10));
		chatBoard.setFont(msgFont);
		
		chatBoard.setLineWrap(true);
		chatBoard.setWrapStyleWord(true);
		chatBoard.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(chatBoard);
        scrollPane.setPreferredSize(new Dimension(515, 350));
		chatBoardPanel = new JPanel();
		chatBoardPanel.add(scrollPane);
	
		chatBoardPanel.setFont(msgFont);
		return chatBoardPanel;
	}
	
	public static JPanel setChatInputsPanel() {
		chatInputPanel = new JPanel(new GridLayout(1, 1, 1, 5));
		chatInputPanel.setBorder(blankBorder);	
		messageInput = new JTextField();
		messageInput.setFont(msgFont);
		chatInputPanel.add(messageInput);
		return chatInputPanel;
	}

	public static JPanel setOnlineUsersPanel() {
		userPanel = new JPanel(new BorderLayout());
		JLabel userLabel = new JLabel("ONLINE", JLabel.CENTER);
		userPanel.add(userLabel, BorderLayout.NORTH);	
		userLabel.setFont(listFont);

		onlineUsersPanel(List.of("No one's here :'("));

		clientPanel.setFont(msgFont);
		userPanel.add(buttonsPanel(), BorderLayout.SOUTH);		
		userPanel.setBorder(blankBorder);

		return userPanel;		
	}

    public static void onlineUsersPanel(List<String> currClients) {  	
    	clientPanel = new JPanel(new BorderLayout());
        listModel = new DefaultListModel<String>();
        
        for (String s : currClients) {
        	listModel.addElement(s);
        }
        if (currClients.size() > 1){
        	privateMsgBtn.setEnabled(true);
        }
        
        //Create the list and put it in a scroll pane.
        onlineUsers = new JList<String>(listModel);
        onlineUsers.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        onlineUsers.setVisibleRowCount(8);
        onlineUsers.setFont(msgFont);
        JScrollPane listScrollPane = new JScrollPane(onlineUsers);

        clientPanel.add(listScrollPane, BorderLayout.CENTER);
        userPanel.add(clientPanel, BorderLayout.CENTER);
    }
	
	public static JPanel buttonsPanel() {		
		msgBtn = new JButton("Message");
		msgBtn.addActionListener(temp);
		msgBtn.setEnabled(false);

        privateMsgBtn = new JButton("Private Message");
        privateMsgBtn.addActionListener(temp);
        privateMsgBtn.setEnabled(false);

        sendFileBtn = new JButton("Send File");
        sendFileBtn.addActionListener(temp);
        sendFileBtn.setEnabled(false);
		
		loginBtn = new JButton("Sign Up");
		loginBtn.addActionListener(temp);
		
		JPanel buttonPanel = new JPanel(new GridLayout(5, 1));
		buttonPanel.add(privateMsgBtn);
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(loginBtn);
		buttonPanel.add(msgBtn);
        buttonPanel.add(sendFileBtn);
		
		return buttonPanel;
	}
}
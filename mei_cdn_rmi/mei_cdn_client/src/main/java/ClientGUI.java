import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

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
    private static JTextPane chatBoard;
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
                    if (user.length() != 0) {
                        guiFrame.setTitle("Hello " + user + "!");
                        messageInput.setText("");
                        appendToChatBoardPanel("SERVER> You are being connected to chat, hold a moment ...\n", Color.green);

                        String cleanUsername = user.replaceAll("\\s+","_").replaceAll("\\W+","_");
                        clientChatStub = new ChatClient();
                        if (!serverAuthStub.login(cleanUsername, clientChatStub)) {
                            JOptionPane.showMessageDialog(guiFrame, "> Error logging in.\nPlease try again later or use a a different username", 
                                "Login failed", JOptionPane.ERROR_MESSAGE);
                        } else {
                            loginBtn.setEnabled(false);
                            msgBtn.setEnabled(true);
                            appendToChatBoardPanel("SERVER> Logged in successfuly! You may start chatting.\n", Color.green);
                        }
                    } else{
                        JOptionPane.showMessageDialog(guiFrame, "> Username required!");
                    }

                } else if(click.getSource() == msgBtn){
                    message = messageInput.getText();
                    messageInput.setText("");
                    // TODO implement send a public chat message
                    // sendMessage(message); to server
                    System.out.println("> Chat message from " + user + ": " + message);

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
        if (guiFrame != null && guiFrame.isVisible()) return;

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

    public static void appendToChatBoardPanel(String msg, Color c) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "monospaced");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = chatBoard.getDocument().getLength();
        chatBoard.setCaretPosition(len);
        chatBoard.setCharacterAttributes(aset, false);
        chatBoard.replaceSelection(msg);
    }

    public static void onlineUsersPanel(List<String> currClients) {
        if (clientPanel != null) userPanel.remove(clientPanel);

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
        userPanel.repaint();
        userPanel.revalidate();
    }

    private static JPanel setChatBoardPanel() {
        EmptyBorder eb = new EmptyBorder(new Insets(10, 10, 10, 10));
		chatBoard = new JTextPane();
		chatBoard.setMargin(new Insets(10, 10, 10, 10));

		chatBoard.setFont(msgFont);
        chatBoard.setBorder(eb);
        //tPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        appendToChatBoardPanel("SERVER> Welcome!\nSERVER> Enter a unique username below and press SIGN UP to begin.\n...\n", Color.GREEN.darker().darker());
		
		//chatBoard.setLineWrap(true);
		//chatBoard.setWrapStyleWord(true);
		chatBoard.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(chatBoard);
        scrollPane.setPreferredSize(new Dimension(515, 350));
		chatBoardPanel = new JPanel();
		chatBoardPanel.add(scrollPane);
	
		chatBoardPanel.setFont(msgFont);
		return chatBoardPanel;
	}

    private static JPanel setChatInputsPanel() {
		chatInputPanel = new JPanel(new GridLayout(1, 1, 1, 5));
		chatInputPanel.setBorder(blankBorder);	
		messageInput = new JTextField();
		messageInput.setFont(msgFont);
		chatInputPanel.add(messageInput);
		return chatInputPanel;
	}

    private static JPanel setOnlineUsersPanel() {
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
	
	private static JPanel buttonsPanel() {		
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
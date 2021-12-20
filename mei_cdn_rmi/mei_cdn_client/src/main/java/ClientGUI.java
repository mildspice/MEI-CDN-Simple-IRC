import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.rmi.RemoteException;
import java.time.Instant;
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
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class ClientGUI {

    // # GUI fields
    private static JFrame guiFrame; // main Frame
	private static JPanel chatBoardPanel, chatInputPanel, generalInputPanel;
	private static JTextField messageInput;
	private static Font msgFont = new Font("monospaced", Font.PLAIN, 12);
    private static Font listFont = new Font("Arial", Font.PLAIN, 14);
	private static Border blankBorder = BorderFactory.createEmptyBorder(3,3,6,3);
    private static JList<String> onlineUsers;
    private static DefaultListModel<String> listModel;
    private static JTextPane chatBoard;
    private static JButton privateMsgBtn, loginBtn, msgBtn, sendFileBtn;
    private static JPanel clientPanel, userPanel;

    // # RMI | IRC fields
    private static String user, message;
    private static AuthInterface serverAuthStub;
    private static FileServerInterface serverFileStub;
    private static ChatClient clientChatStub;
    private static FileClient clientFileManager;

    private static ActionListener temp = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent click) {
            try {
                if (click.getSource() == loginBtn) {
                    user = messageInput.getText();				
                    if (user.length() != 0) {
                        guiFrame.setTitle("Hello " + user + "!");
                        messageInput.setText("");
                        appendToChatBoardPanel("SERVER> You are being connected to chat, hold a moment ...\n", Color.green);

                        String cleanUsername = user.replaceAll("\\s+","_").replaceAll("\\W+","_");
                        clientChatStub = new ChatClient();
                        clientFileManager = new FileClient();
                        if (!serverAuthStub.login(cleanUsername, clientChatStub)) {
                            JOptionPane.showMessageDialog(guiFrame, "> Error logging in.\nPlease try again later or use a a different username", 
                                "Login failed", JOptionPane.ERROR_MESSAGE);
                        } else {
                            loginBtn.setEnabled(false);
                            msgBtn.setEnabled(true);
                            sendFileBtn.setEnabled(true);
                            privateMsgBtn.setEnabled(true);
                            appendToChatBoardPanel("SERVER> Logged in successfuly! You may start chatting.\n", Color.green);
                        }
                    } else{
                        JOptionPane.showMessageDialog(guiFrame, "> Username required!");
                    }

                } else if (click.getSource() == msgBtn) {
                    message = messageInput.getText();
                    messageInput.setText("");
                    // TODO implement send a public chat message using ChatServer STUB
                    // sendMessage(message); to server
                    System.out.println("> Chat message from " + user + ": " + message);

                } else if (click.getSource() == sendFileBtn) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                    int returnValue = fileChooser.showOpenDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        String extension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".")),
                                name = selectedFile.getName().substring(0, selectedFile.getName().lastIndexOf("."));

                        clientFileManager.uploadFileToServer(serverFileStub, name + "_" + Instant.now().toEpochMilli() + extension, selectedFile.getAbsolutePath());
                        // TODO check if file was uploaded before sending chat message
                        
                        message = messageInput.getText();
                        messageInput.setText("");
                        // TODO implement send a public chat message using ChatServer STUB
                        // sendMessage(message, fileName); to server
                        System.out.println("> Chat message from " + user + ": " + message);
                    }

                } else if (click.getSource() == privateMsgBtn) {
                    //List<String> selectedUsers = onlineUsers.getSelectedValuesList();
                    // TODO implement send a private chat message using ChatServer STUB
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

    public static void openChatMainWindow(AuthInterface authStub, FileServerInterface filesStub) {
        if (guiFrame != null && guiFrame.isVisible()) return;

        serverAuthStub = authStub;
        serverFileStub = filesStub;
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
        //guiFrame.setAlwaysOnTop(true);
        guiFrame.setResizable(false);
		guiFrame.setLocation(150, 150);
        //guiFrame.setPreferredSize(new Dimension(700, 450));

		JPanel chatBoardOuterPanel = new JPanel(new BorderLayout());
        chatBoardOuterPanel.add(setGeneralInputsPanel(), BorderLayout.NORTH);
		chatBoardOuterPanel.add(setChatBoardPanel(), BorderLayout.CENTER);
        chatBoardOuterPanel.add(setChatInputsPanel(), BorderLayout.SOUTH);
		
		guiFrame.setLayout(new BorderLayout());
		guiFrame.add(chatBoardOuterPanel, BorderLayout.CENTER);
		guiFrame.add(setOnlineUsersPanel(), BorderLayout.EAST);

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

    	clientPanel = new JPanel(new BorderLayout(20, 10));
        clientPanel.setFont(listFont);
        listModel = new DefaultListModel<String>();
        
        for (String s : currClients) {
        	listModel.addElement(s);
        }
        if (currClients.size() > 1){
        	privateMsgBtn.setEnabled(true);
        }
        
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

    private static JPanel setOnlineUsersPanel() {
		userPanel = new JPanel(new BorderLayout());
		// JLabel userLabel = new JLabel("ONLINE", JLabel.CENTER);	
		// userLabel.setFont(listFont);
        // userPanel.add(userLabel, BorderLayout.SOUTH);

		onlineUsersPanel(List.of("No one's here :'("));

        userPanel.setPreferredSize(new Dimension(150, 100));
		userPanel.setBorder(BorderFactory.createEmptyBorder(3,1,3,1));

		return userPanel;		
	}

    private static JPanel setChatBoardPanel() {
        chatBoard = new JTextPane();
        appendToChatBoardPanel("SERVER> Welcome!\nSERVER> Enter a unique username below and press SIGN UP to begin.\n...\n", Color.GREEN.darker().darker());
		chatBoard.setMargin(new Insets(10, 10, 10, 10));
        //chatBoard.setBorder(blankBorder);
        //chatBoard.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		chatBoard.setFont(msgFont);
		//chatBoard.setLineWrap(true);
		//chatBoard.setWrapStyleWord(true);
		chatBoard.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(chatBoard);
        scrollPane.setPreferredSize(new Dimension(750, 350));
		chatBoardPanel = new JPanel();
		chatBoardPanel.add(scrollPane);
	
		chatBoardPanel.setFont(msgFont);
		return chatBoardPanel;
	}

    private static JPanel setChatInputsPanel() {
		chatInputPanel = new JPanel(new GridLayout(2, 1, 1, 2));
		chatInputPanel.setBorder(blankBorder);	
        
		messageInput = new JTextField();
        messageInput.setMargin(new Insets(5, 5, 5, 5));
		messageInput.setFont(msgFont);

        msgBtn = new JButton("Message");
		msgBtn.addActionListener(temp);
		msgBtn.setEnabled(false);

        privateMsgBtn = new JButton("Private Message");
        privateMsgBtn.addActionListener(temp);
        privateMsgBtn.setEnabled(false);

        sendFileBtn = new JButton("Send File");
        sendFileBtn.addActionListener(temp);
        sendFileBtn.setEnabled(false);
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 5));
        buttonPanel.add(msgBtn);
        buttonPanel.add(sendFileBtn);
        buttonPanel.add(new JLabel(""));
        buttonPanel.add(privateMsgBtn);

		chatInputPanel.add(messageInput);
        chatInputPanel.add(buttonPanel);
		return chatInputPanel;
	}

    private static JPanel setGeneralInputsPanel() {
		generalInputPanel = new JPanel(new GridLayout(1, 1, 1, 2));
		generalInputPanel.setBorder(blankBorder);	
		
        loginBtn = new JButton("Sign Up");
		loginBtn.addActionListener(temp);
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 1));
        buttonPanel.add(loginBtn);

        generalInputPanel.add(buttonPanel);
		return generalInputPanel;
	}
}
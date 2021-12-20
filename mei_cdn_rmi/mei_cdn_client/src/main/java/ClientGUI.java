import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.rmi.RemoteException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
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
import javax.swing.event.MouseInputListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.html.HTML;

public class ClientGUI {

    // # GUI fields
    private static JFrame guiFrame; // main Frame
    private static JPanel chatBoardPanel, chatInputPanel, generalInputPanel;
    private static JTextField messageInput;
    private static Font msgFont = new Font("monospaced", Font.PLAIN, 12);
    private static Font listFont = new Font("Arial", Font.PLAIN, 14);
    private static Border blankBorder = BorderFactory.createEmptyBorder(3, 3, 6, 3);
    private static JList<String> onlineUsers;
    private static DefaultListModel<String> listModel;
    private static JTextPane chatBoard;
    private static JButton privateMsgBtn, privateMsgFileBtn, loginBtn, msgBtn, msgFileBtn;
    private static JPanel clientPanel, userPanel;

    // # RMI | IRC fields
    private static String user, message;
    private static AuthInterface serverAuthStub;
    private static FileServerInterface serverFileStub;
    private static ChatServerInterface serverChatStub;

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
                        appendToChatBoardPanel("SERVER> You are being connected to chat, hold a moment ...\n",
                                Color.green.darker().darker());

                        // # CHAT HISTORY IS UPDATED BY THE SERVER

                        String cleanUsername = user.replaceAll("\\s+", "_").replaceAll("\\W+", "_");
                        clientChatStub = new ChatClient();
                        clientFileManager = new FileClient();
                        if (!serverAuthStub.login(cleanUsername, clientChatStub)) {
                            JOptionPane.showMessageDialog(guiFrame, "User may be already logged in!",
                                    "Login failed", JOptionPane.WARNING_MESSAGE);
                        } else {
                            loginBtn.setEnabled(false);
                            msgBtn.setEnabled(true);
                            msgFileBtn.setEnabled(true);
                            privateMsgBtn.setEnabled(true);
                            appendToChatBoardPanel("SERVER> Logged in successfuly! You may start chatting.\n",
                                    Color.green.darker().darker());
                        }
                    } else {
                        JOptionPane.showMessageDialog(guiFrame, "Username required!");
                    }

                } else if (click.getSource() == msgBtn) {
                    message = messageInput.getText();
                    messageInput.setText("");
                    serverChatStub.updateChat(new Message(user, message, false));

                } else if (click.getSource() == msgFileBtn) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                    int returnValue = fileChooser.showOpenDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        String extension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".")),
                                name = selectedFile.getName().substring(0, selectedFile.getName().lastIndexOf(".")),
                                serverName = name + "_" + Instant.now().toEpochMilli() + extension;

                        if (clientFileManager.uploadFileToServer(serverFileStub, serverName, selectedFile)) {
                            message = messageInput.getText();
                            messageInput.setText("");
                            serverChatStub.updateChat(new Message(user, message, false, serverName, selectedFile));
                        }
                    }

                } else if (click.getSource() == privateMsgBtn) {
                    List<String> selectedUsers = onlineUsers.getSelectedValuesList();
                    selectedUsers.add(user);

                    message = messageInput.getText();
                    messageInput.setText("");
                    serverChatStub.updateChat(new Message(user, message, true));
                } else if (click.getSource() == privateMsgFileBtn) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                    int returnValue = fileChooser.showOpenDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        String extension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".")),
                                name = selectedFile.getName().substring(0, selectedFile.getName().lastIndexOf(".")),
                                serverName = name + "_" + Instant.now().toEpochMilli() + extension;

                        if (clientFileManager.uploadFileToServer(serverFileStub, serverName, selectedFile)) {
                            message = messageInput.getText();
                            messageInput.setText("");
                            serverChatStub.updateChat(new Message(user, message, true, serverName, selectedFile));
                        }
                    }

                }
            } catch (RemoteException remoteExc) {
                remoteExc.printStackTrace();
            }
        }
    };

    public static void openChatMainWindow(AuthInterface authStub, FileServerInterface filesStub,
            ChatServerInterface chatStub) {
        if (guiFrame != null && guiFrame.isVisible())
            return;

        serverAuthStub = authStub;
        serverFileStub = filesStub;
        serverChatStub = chatStub;
        guiFrame = new JFrame("Simple IRC");

        guiFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {

                if (serverAuthStub != null && user != null && !user.isEmpty()) {
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
        // guiFrame.setAlwaysOnTop(true);
        guiFrame.setResizable(false);
        guiFrame.setLocation(150, 150);
        // guiFrame.setPreferredSize(new Dimension(700, 450));

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

    public static void appendToChatBoardPanel(String msg, Color color) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "monospaced");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = chatBoard.getDocument().getLength();
        chatBoard.setEditable(true);
        chatBoard.setCaretPosition(len);
        chatBoard.setCharacterAttributes(aset, false);
        chatBoard.replaceSelection(msg);
        chatBoard.setEditable(false);
    }

    public static void appendLinkToChatBoardPanel(String url, Color color) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "monospaced");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
        aset = sc.addAttribute(aset, StyleConstants.Underline, true);
        aset = sc.addAttribute(aset, HTML.Attribute.HREF, url.toString());

        int len = chatBoard.getDocument().getLength();
        chatBoard.setEditable(true);
        chatBoard.setCaretPosition(len);
        chatBoard.setCharacterAttributes(aset, false);
        chatBoard.replaceSelection(url);
        chatBoard.setEditable(false);
    }

    public static void onlineUsersPanel(List<String> currClients) {
        if (clientPanel != null)
            userPanel.remove(clientPanel);

        clientPanel = new JPanel(new BorderLayout(20, 10));
        clientPanel.setFont(listFont);
        listModel = new DefaultListModel<String>();

        for (String s : currClients.stream().filter(usr -> !usr.equalsIgnoreCase(user)).collect(Collectors.toList())) {
            listModel.addElement(s);
        }
        if (currClients.size() > 1) {
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
        userPanel.setBorder(BorderFactory.createEmptyBorder(3, 1, 3, 1));

        return userPanel;
    }

    private static JPanel setChatBoardPanel() {
        chatBoard = new JTextPane();
        chatBoard.setMargin(new Insets(10, 10, 10, 10));
        // chatBoard.setBorder(blankBorder);
        // chatBoard.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        chatBoard.setFont(msgFont);
        // chatBoard.setLineWrap(true);
        // chatBoard.setWrapStyleWord(true);
        appendToChatBoardPanel(
                "SERVER> Welcome!\nSERVER> Enter a unique username below and press SIGN UP to begin.\n...\n",
                Color.GREEN.darker().darker());
        chatBoard.setEditable(false);
        LinkController handler = new LinkController();
        chatBoard.addMouseListener(handler);
        chatBoard.addMouseMotionListener(handler);

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

        msgFileBtn = new JButton("Send File");
        msgFileBtn.addActionListener(temp);
        msgFileBtn.setEnabled(false);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 6));
        buttonPanel.add(msgBtn);
        buttonPanel.add(msgFileBtn);
        buttonPanel.add(new JLabel(""));
        buttonPanel.add(privateMsgBtn);
        buttonPanel.add(privateMsgFileBtn);

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

    private static class LinkController extends MouseAdapter implements MouseInputListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            JTextPane chatBoard = (JTextPane) e.getSource();
            Document doc = chatBoard.getDocument();
            Point pt = new Point(e.getX(), e.getY());
            int pos = chatBoard.viewToModel2D(pt);

            if (pos >= 0) {
                if (doc instanceof DefaultStyledDocument) {
                    DefaultStyledDocument hdoc = (DefaultStyledDocument) doc;
                    Element el = hdoc.getCharacterElement(pos);
                    AttributeSet a = el.getAttributes();
                    String file = (String) a.getAttribute(HTML.Attribute.HREF);

                    if (file != null) {
                        try {
                            // # GET FILE IN EXPLORER
                            Runtime.getRuntime().exec("explorer.exe /select," + file);

                            // # OPEN FILE
                            // java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
                            // desktop.open(new File(file));

                            // # OPEN BROWSER URL
                            // java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
                            // java.net.URI uri = new java.net.URI(href);
                            // desktop.browse(uri);
                        } catch (Exception ev) {
                            System.err.println(ev.getMessage());
                        }
                    }
                }
            }
        }

        @Override
        public void mouseMoved(MouseEvent ev) {

            Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
            Cursor defaultCursor = Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR);
            JTextPane chatBoard = (JTextPane) ev.getSource();
            Point pt = new Point(ev.getX(), ev.getY());
            int pos = chatBoard.viewToModel2D(pt);

            if (pos >= 0) {
                Document doc = chatBoard.getDocument();

                if (doc instanceof DefaultStyledDocument) {
                    DefaultStyledDocument hdoc = (DefaultStyledDocument) doc;
                    Element e = hdoc.getCharacterElement(pos);
                    AttributeSet a = e.getAttributes();
                    String href = (String) a.getAttribute(HTML.Attribute.HREF);

                    if (href != null) {
                        if (guiFrame.getCursor() != handCursor) {
                            chatBoard.setCursor(handCursor);
                        }
                    } else {
                        chatBoard.setCursor(defaultCursor);
                    }
                }
            }
            else {
                chatBoard.setToolTipText(null);
            }
        }
    }

}
package edu.jsu.mcis.seabattleii;

import edu.jsu.mcis.seabattleii.AbstractView;
import edu.jsu.mcis.seabattleii.DefaultController;
import edu.jsu.mcis.seabattleii.ViewGridContainer;
import edu.jsu.mcis.seabattleii.ViewPrimaryGrid;
import edu.jsu.mcis.seabattleii.ViewTrackingGrid;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class ViewMainWindow
extends JFrame
implements AbstractView {
    private static final String PLAYER_1 = "Player 1";
    private static final String PLAYER_2 = "Player 2";
    private static final String EMPTY_CARD = "Empty";
    private static final String ICON_ROOT = "/resources/images/";
    private static final String BANNER_ICON_FILE = "banner.png";
    private static final String SOUND_ROOT = "/resources/sounds/";
    private static final String SPLASH_SOUND_FILE = "splash.wav";
    private static final String BOOM_SOUND_FILE = "boom.wav";
    private boolean soundEnabled;
    AudioClip splash;
    AudioClip boom;
    ViewPrimaryGrid p1;
    ViewPrimaryGrid p2;
    ViewTrackingGrid t1;
    ViewTrackingGrid t2;
    ViewGridContainer c1;
    ViewGridContainer c2;
    ViewGridContainer c3;
    ViewGridContainer c4;
    JPanel container;
    JPanel cards;
    JLabel banner;
    JLabel status;
    int currentPlayer;

    public ViewMainWindow(DefaultController controller, ViewPrimaryGrid p1, ViewTrackingGrid t1, ViewPrimaryGrid p2, ViewTrackingGrid t2) {
        super("Sea Battle II");
        this.p1 = p1;
        this.p2 = p2;
        this.t1 = t1;
        this.t2 = t2;
        this.initComponents();
        this.currentPlayer = 1;
        this.soundEnabled = true;
        this.showCard(PLAYER_1);
    }

    private void initComponents() {
        this.container = new JPanel();
        this.container.setLayout(new BorderLayout());
        this.container.setBackground(Color.BLACK);
        this.banner = new JLabel("", 0);
        this.banner.setBorder(BorderFactory.createEmptyBorder(16, 0, 0, 0));
        this.banner.setIcon(new ImageIcon(this.getClass().getResource("/resources/images/banner.png")));
        this.status = new JLabel("", 0);
        this.status.setPreferredSize(new Dimension(0, 32));
        this.status.setForeground(Color.WHITE);
        this.splash = Applet.newAudioClip(this.getClass().getResource("/resources/sounds/splash.wav"));
        this.boom = Applet.newAudioClip(this.getClass().getResource("/resources/sounds/boom.wav"));
        this.c1 = new ViewGridContainer((JPanel)this.p1);
        this.c1.setTitle("Player 1: Primary Grid");
        this.c2 = new ViewGridContainer((JPanel)this.t1);
        this.c2.setTitle("Player 1: Tracking Grid");
        this.c3 = new ViewGridContainer((JPanel)this.p2);
        this.c3.setTitle("Player 2: Primary Grid");
        this.c4 = new ViewGridContainer((JPanel)this.t2);
        this.c4.setTitle("Player 2: Tracking Grid");
        this.cards = new JPanel();
        this.cards.setLayout(new CardLayout());
        this.cards.setBackground(Color.BLACK);
        JPanel card1 = new JPanel();
        card1.setLayout(new BorderLayout());
        card1.add((Component)this.c1, "Before");
        card1.add((Component)this.c2, "After");
        card1.setVisible(false);
        JPanel card2 = new JPanel();
        card2.setLayout(new BorderLayout());
        card2.add((Component)this.c3, "Before");
        card2.add((Component)this.c4, "After");
        card2.setVisible(false);
        JPanel empty = new JPanel();
        empty.setLayout(new BorderLayout());
        empty.setBackground(Color.BLACK);
        empty.setVisible(false);
        this.cards.add((Component)card1, PLAYER_1);
        this.cards.add((Component)card2, PLAYER_2);
        this.cards.add((Component)empty, EMPTY_CARD);
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenuItem menuItemSounds = new JMenuItem("Enable/Disable Sounds");
        JMenuItem menuItemExit = new JMenuItem("Exit");
        menuFile.add(menuItemSounds);
        menuFile.add(menuItemExit);
        menuItemSounds.addActionListener(e -> {
            this.soundEnabled = !this.soundEnabled;
        });
        menuItemExit.addActionListener(e -> System.exit(0));
        JMenu menuHelp = new JMenu("Help");
        JMenuItem menuItemAbout = new JMenuItem("About");
        menuHelp.add(menuItemAbout);
        menuItemAbout.addActionListener(e -> this.showAboutDialog());
        menuBar.add(menuFile);
        menuBar.add(menuHelp);
        this.setJMenuBar(menuBar);
        this.container.add((Component)this.banner, "First");
        this.container.add((Component)this.cards, "Center");
        this.container.add((Component)this.status, "Last");
        this.getContentPane().add(this.container);
    }

    private void showCard(String name) {
        CardLayout cl = (CardLayout)this.cards.getLayout();
        cl.show(this.cards, name);
    }

    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this, "Sea Battle II Version 1.0\nCS 232 (Computer Programming II), Fall 2018");
    }

    private int otherPlayer() {
        return this.currentPlayer == 1 ? 2 : 1;
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent e) {
        if (e.getPropertyName().equals("Status")) {
            String newStatus = e.getNewValue().toString();
            if (!this.status.getText().equals(newStatus)) {
                this.status.setText(newStatus);
            }
        } else if (e.getPropertyName().equals("Player1ShotMissed") && this.currentPlayer == 1 || e.getPropertyName().equals("Player2ShotMissed") && this.currentPlayer == 2) {
            if (this.soundEnabled) {
                this.splash.play();
            }
            JOptionPane.showMessageDialog(this, "Your shot was a MISS!");
            this.showCard(EMPTY_CARD);
            JOptionPane.showMessageDialog(this, "Player " + this.otherPlayer() + ": Click \"OK\" to begin your turn ...");
            this.currentPlayer = this.otherPlayer();
            this.showCard(this.currentPlayer == 1 ? PLAYER_1 : PLAYER_2);
        } else if (e.getPropertyName().equals("Player2ShipHit") && this.currentPlayer == 1 || e.getPropertyName().equals("Player1ShipHit") && this.currentPlayer == 2) {
            if (this.soundEnabled) {
                this.boom.play();
            }
            String ship = e.getNewValue().toString();
            JOptionPane.showMessageDialog(this, "Your shot HIT a " + ship + "!");
            this.showCard(EMPTY_CARD);
            JOptionPane.showMessageDialog(this, "Player " + this.otherPlayer() + ": Click \"OK\" to begin your turn ...");
            this.currentPlayer = this.otherPlayer();
            this.showCard(this.currentPlayer == 1 ? PLAYER_1 : PLAYER_2);
        } else if (e.getPropertyName().equals("Player2ShipSunk") && this.currentPlayer == 1 || e.getPropertyName().equals("Player1ShipSunk") && this.currentPlayer == 2) {
            if (this.soundEnabled) {
                this.boom.play();
            }
            String ship = e.getNewValue().toString();
            JOptionPane.showMessageDialog(this, "Your shot SANK a " + ship + "!");
            this.showCard(EMPTY_CARD);
            JOptionPane.showMessageDialog(this, "Player " + this.otherPlayer() + ": Click \"OK\" to begin your turn ...");
            this.currentPlayer = this.otherPlayer();
            this.showCard(this.currentPlayer == 1 ? PLAYER_1 : PLAYER_2);
        } else if (e.getPropertyName().equals("Player2GameOver") && this.currentPlayer == 1 || e.getPropertyName().equals("Player1GameOver") && this.currentPlayer == 2) {
            if (this.soundEnabled) {
                this.boom.play();
            }
            this.showCard(EMPTY_CARD);
            JOptionPane.showMessageDialog(this, "The game is ended ...  Player" + this.currentPlayer + " wins ...");
        }
    }
}
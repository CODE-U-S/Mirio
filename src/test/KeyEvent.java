package test;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
public class KeyEvent extends JFrame implements KeyListener{
       Image image;
       int x = 100, y = 100, sel = 1;
       
       public KeyEvent() {
              super("고양이 이동시키기"); // 프로그램 명
              
              //프로그램 창에 아이콘 이미지 표시, 파비콘
              setIconImage(Toolkit.getDefaultToolkit().getImage("images/cat1.png")); // 파비콘 위치
              
              setLayout(null);      //레이아웃 매니저 사용 안함
              setResizable(false);  //창 크기 고정
              setBounds(200, 200, 300, 300); //창 크기 지정
              setBackground(Color.WHITE); //창 배경 색상 지정
              setVisible(true);
              
              setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프로그램 닫힘 설정
              
              addKeyListener(this);  //frame에 KeyListener 장착
              
              x = (int)getWidth() / 2;   //frame의 너비 얻기
              y = (int)getHeight() / 2;
       }
       
       @Override
       public void keyPressed(java.awt.event.KeyEvent e) {
              int key = e.getKeyCode();
                     if(key == e.VK_RIGHT ||
                           key == e.VK_NUMPAD6 || //숫자키 NUMPAD
                                  key == e.VK_KP_RIGHT) {
                     sel = (sel == 1)?2:1;  //삼항연산자
                     x = (x < getWidth())?x + 10 : -image.getWidth(this);
                     }
                     else{int key1 = e.getKeyCode();
                                  if(key1 == e.VK_LEFT ||
                                         key1 == e.VK_NUMPAD4 || //숫자키 NUMPAD 
                                                key1 == e.VK_KP_LEFT) {
                                  sel = (sel == 1)?3:1;  //삼항연산자 
                                  x = (x > 0)?x - 10 :getWidth() + image.getWidth(this);
                     }
                                  int key2 = e.getKeyCode();
                                  if(key2 == e.VK_UP ||
                                         key2 == e.VK_NUMPAD8 || //숫자키 NUMPAD 
                                                key2 == e.VK_KP_UP) {
                                  sel = (sel == 1)?4:1;  //삼항연산자 
                                  y = (y > 0)?y - 10 : getHeight() + image.getHeight(this);
                                         
                     }
                                  int key3 = e.getKeyCode();
                                  if(key3 == e.VK_DOWN ||
                                         key3 == e.VK_NUMPAD2 || //숫자키 NUMPAD 
                                                key3 == e.VK_KP_DOWN) {
                                  sel = (sel == 1)?4:1;  //삼항연산자
                                  y = (y < getHeight())?y + 10 : image.getWidth(this);
                                  
              
       }
       }
       repaint(); //한번 실행하면 다시 원래 이미지로 복귀
       }
              
       
       @Override
       public void paint(Graphics g) {
              switch (sel) {
              case 1:
                     image = Toolkit.getDefaultToolkit().getImage("images/cat1.png"); //고양이 이미지1
                     break;
              case 2:
                     image = Toolkit.getDefaultToolkit().getImage("images/cat2.png"); //고양이 이미지2
                     break;
                     
              case 3:
                     image = Toolkit.getDefaultToolkit().getImage("images/cat3.png"); //고양이 이미지3
                     break;
                     
              case 4:
                     image = Toolkit.getDefaultToolkit().getImage("images/cat4.png"); //고양이 이미지4
                     break;
              }
              
              g.clearRect(0, 0, getWidth(), getHeight());  //화면의 잔상 클리어
              g.drawImage(image,
                           x - image.getWidth(this) / 2,
                           y - image.getHeight(this) / 2, this);
       }
       
       @Override
       public void keyReleased(java.awt.event.KeyEvent arg0) {}
       @Override
       public void keyTyped(java.awt.event.KeyEvent arg0) {}
       
       public static void main(String[] args) {
              new KeyEvent();
       }
}

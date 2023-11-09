//package test;
//
//import javax.swing.ImageIcon;
//import javax.swing.JLabel;
//
///**
// * 
// * @author Administrator 플레이어는 좌우이동, 점프가 가능하다. 방울을 발사하며 공격을 한다.(나중에) 플레이어의 위치는
// *         x, y좌표로 지정해준다. x좌표로 좌우이동, y좌표로 점프 실행
// *
// */
//
//public class Player extends JLabel {
//	private int x; // 플레이어의 x좌표
//	private int y; // 플레이어의 y좌표
//
//	private ImageIcon playerR; // 오른쪽을 보고있는 플레이어 Image
//	private ImageIcon playerL; // 왼쪽을 보고있는 플레이어 Image
//
//	private boolean isRight; // 보통 boolean타입 변수 이름에는 is가 붙음
//	private boolean isLeft;
//	private boolean isJump;
//
//	private static final int JUMPSPEED = 2; // 점프 속도
//	private static final int SPEED = 4; // 이동 속도
//
//	// 자바 특징 : is가 붙은 boolean 변수의 getter는 getIsRight가 아닌 isRight라고 이름이 붙음
//	public boolean isRight() {
//		return isRight;
//	}
//
//	// 자바 특징 : is가 붙은 boolean 변수의 setter는 setIsRight가 아닌 setRight라고 이름이 붙음
//	public void setRight(boolean isRight) {
//		this.isRight = isRight;
//	}
//
//	public boolean isLeft() {
//		return isLeft;
//	}
//
//	public void setLeft(boolean isLeft) {
//		this.isLeft = isLeft;
//	}
//
//	public boolean isJump() {
//		return isJump;
//	}
//
//	public void setJump(boolean isJump) {
//		this.isJump = isJump;
//	}
//
//	public Player() {
//		initObject();
//		initSetting();
//	}
//
//	private void initObject() {
//		playerR = new ImageIcon("images/jinseon_up.png");
//		playerL = new ImageIcon("images/jinseon.png");
//	}
//
//	// 생성자에서 초기화
//	// 생성자에 호출되어있는 메서드니까 얘도 생성자 취급!
//	private void initSetting() {
//		x = 70;
//		y = 535;
//		setIcon(playerL);
//		setSize(100, 80);
//		setLocation(x, y); // paintComponent 호출, 부분 새로고침
//
//		// 처음 시작엔 가만히 있는 상태
//		isRight = false;
//		isLeft = false;
//		isJump = false;
//
//	}
//
//	// 하나의 단일 책임을 가진 메서드, 메서드 모듈
//	// 외부에서 호출 가능하게 public
//	public void right() {
//		isRight = true;
//
//		System.out.println("오른쪽 이동");
//
//		
//
//		// 새로운 스레드 생성
//		new Thread(() -> {
//			// isRight가 true일때만 이동
//			while (isRight) {
//				x = x + SPEED;
//				setLocation(x, y); // 그림 다시 그리기
//				try {
//					Thread.sleep(10);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}
//
//	public void left() {
//		isLeft = true;
//
//		System.out.println("왼쪽 이동");
//
//		
//
//		// 새로운 스레드 생성
//		new Thread(() -> {
//			// isLeft가 true일때만 이동
//			while (isLeft) {
//				x = x - SPEED;
//				setLocation(x, y); // 그림 다시 그리기
//				try {
//					Thread.sleep(10);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}
//
//	public void jump() {
//		isJump = true;
//
//		System.out.println("위로 점프");
//		// 점프는 for문을 돌려야함
//		// up : sleep(5), down : sleep(3)
//		setIcon(playerR);
//
//		// 새로운 스레드 생성
//		new Thread(() -> {
//			
//			// up
//			for (int i = 0; i < 130 / JUMPSPEED; i++) { // JUMPSPEED에 따라 높이가 달라지면 안됨!
//				y = y - JUMPSPEED;
//				setLocation(x, y); // 그림 다시 그리기
//
//				try {
//					Thread.sleep(2);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//			
//			 // Pause in the air
//	        try {
//	            Thread.sleep(60); // Pause for 0.5 seconds (500 milliseconds)
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }
//
//			// down
//			// 바닥에 떨어지는 건 while로 수정!
//			for (int i = 0; i < 130 / JUMPSPEED; i++) {
//				y = y + JUMPSPEED;
//				
//				setLocation(x, y); // 그림 다시 그리기
//				try {
//					Thread.sleep(3);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//			setIcon(playerL);
//			isJump = false;
//
//		}).start();
//	}
//}
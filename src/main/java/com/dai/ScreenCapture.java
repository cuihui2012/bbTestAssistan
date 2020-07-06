package com.dai;

import cn.novelweb.tool.video.recording.ScreenRecorder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>屏幕抓取桌面程序</p>
 * <p>2019-12-11 17:05</p>
 *
 * @author Dai Yuanchuan
 **/
@Slf4j
public class ScreenCapture extends Application {

    /**
     * 获取录屏程序对象
     * @return 录屏程序对象
     */
    private ScreenRecorder getScreenRecorder() {

        // 首先你需要构建录屏程序对象,这个是录屏的核心
        // 然后你可以定义一些你需要的参数,直接调用对应的set方法就行

        // 这里不设置任何参数,直接采用默认值...

        // 所有可设置的参数参考 com.dai.RecordingParameters

        return new ScreenRecorder();
    }

    @Override
    public void start(Stage primaryStage) {
        // 设置完参数后就可以使用ScreenRecorder内置的一些方法了
        // start() -> pause() -> stop()
        // 注意上面三个方法是顺序调用的
        // 也就是说,当你调用了stop后 才能重新、也只能调用start
        // 而且这三个方法在一个轮回期间 只能调用一次.

        // 如果你想要正确的生成录屏文件
        // 一定要调用stop方法,不可以直接停止程序

        ScreenRecorder screenRecorder = getScreenRecorder();

        // 按钮、布局、样式、面板等等
        Button start = new Button();
        Button pause = new Button();
        Button stop = new Button();
        start.setText("开始");
        start.getStyleClass().add("start");
        start.setOnAction((ActionEvent event) -> {
            log.info("现在开始录屏啦~~~");

            // 调用录屏的开始程序
            // 调用start方法之后会进行一个短暂的初始化
            // 会在getSaveTo()下面生成一个*.mp4文件
            screenRecorder.start();

            // 生成的视频文件名默认是使用当前时间的毫秒数
            // 如果你需要自定义生成文件名称,可以将你的文件名传入start方法
            // 就像这样,注意文件名不需要加上后缀
            // start("201912录屏文件");

            pause.setDisable(false);
            stop.setDisable(false);
            start.setDisable(true);
        });

        pause.setText("暂停");
        pause.getStyleClass().add("pause");
        pause.setOnAction((ActionEvent event) -> {
            log.info("暂停录屏,你可以一会再开始~~");

            // 调用录屏暂停程序
            // 暂停之后可以调用 start() 方法重新开始
            // 也可以直接调用 stop() 方法结束本次录制
            screenRecorder.pause();

            start.setDisable(false);
            stop.setDisable(false);
            pause.setDisable(true);
        });
        pause.setDisable(true);


        stop.setText("结束");
        stop.getStyleClass().add("stop");
        stop.setOnAction((ActionEvent event) -> {
            log.info("你结束了本次录屏,如果你还需要录制,可以重新点击开始按钮~~");

            // 调用录屏停止程序
            // 停止完成之后,才可以重新调用start方法
            // 进行下一个录屏的轮回
            screenRecorder.stop();

            start.setDisable(false);
            stop.setDisable(true);
            pause.setDisable(true);
        });
        stop.setDisable(true);


        HBox hbox = new HBox();
        hbox.setSpacing(10);
        // 设置居中
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(start, pause, stop);
        hbox.setStyle("-fx-font: 20 arial;-fx-font-family: 'Microsoft YaHei';");


        Scene scene = new Scene(hbox, 300, 80);
        primaryStage.setTitle("屏幕录制程序v1.0");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

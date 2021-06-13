# SoundTypeChecker

## 概要
AndroidのAudioManagerやAudioAttributesにて用意されているストリームタイプの特徴を調査するためのアプリです。

## 操作方法
設定されているストリームタイプのうち一つをプルダウンメニューから自由に設定し、"鳴らすボタン"で音を鳴らします。
Androidをミュート状態にしたり、イヤホンを接続したりといろいろ試してみてください。

AudioManagerかAudioAttributesのどちらを使用するかをラジオボタンで制御しています。
今回使用しているSoundPoolによる音声出力では、AudioManagerとAudioAttributesでストリームの設定方法が異なります。
前者はAndroid 5未満で推奨されていますが、後者はAndroid 5以上で推奨されています。

音量ゲージを使用し、各ストリームタイプの音量を変更できます。
AudioAttributesでは各ストリームタイプの音量が変更できないため、AudioManagerのストリームタイプで変更する必要があります。
それぞれの対応については以下のページを参照してください。
https://source.android.google.cn/devices/audio/attributes?hl=ja#compatibility

## 必要環境
- Android 5.0 ~ Android 11.0
    - 手持ちのAndroid 10て動作確認済み

# 実行環境
以下の環境で実行確認済み
- Android Studio : 4.2
- jdk : 11
- gradle : 6.7.1

## インストール方法
- 1. 以下のURLにアクセス
    - https://github.com/ace51240/SoundTypeChecker
- 2. Actionsタブに移動
- 3. 最新のworkflowをクリック
- 4. Artifactsのapkファイルリンクをクリックし、ダウンロード
- 5. ダウンロードしたapkファイルを手持ちのAndroidでインストール

## 作成経緯
通話アプリのイヤホン対応の実装で、イヤホン接続状態で着信時にアプリの音声をスピーカーから鳴らしたくないとの依頼があり、ストリームタイプを変更することで対応しました。
そのときに他にどのような種類や特徴があるか気になったため、このサンプルアプリを作成するに至りました。
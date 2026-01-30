[![GitHub All Releases](https://img.shields.io/github/v/release/Legotatsu1985/Every-Meal-Recommender)](https://github.com/Legotatsu1985/Every-Meal-Recommender/releases)
[![GitHub Commit Activity](https://img.shields.io/github/commit-activity/w/Legotatsu1985/Every-Meal-Recommender
)](https://github.com/Legotatsu1985/Every-Meal-Recommender/commits/master/)
[![GitHub Last Commit](https://img.shields.io/github/last-commit/Legotatsu1985/Every-Meal-Recommender
)](https://github.com/Legotatsu1985/Every-Meal-Recommender/commits/master/)
![GitHub Downloads Count](https://img.shields.io/github/downloads/Legotatsu1985/Every-Meal-Recommender/total
)
# このアプリについて
「Every-Meal-Recommender」は、毎日の食事のメニューを提案＆管理するためのアプリです。
ユーザーは、自分の好みに合わせて食材を選択し、Google AI Geminiを活用して最適なメニューを提案します。<br>
提案されたメニューは、提案された日時ごとに保存され、過去のメニューを簡単に確認できます。

# 使い方
1. **ファイルのダウンロード**: [Releases](https://github.com/Legotatsu1985/Every-Meal-Recommender/releases)から最新のリリースをダウンロードします。
2. **アプリの起動**: ダウンロードしたファイルを好きな場所に展開し、'Every-Meal-Recommender.jar'をダブルクリックして起動します。<br>（もしエラーが表示されて起動しない場合は、下記のセクションをご覧ください。）
3. **APIキーの設定**: 初回起動時は、Google AI GeminiのAPIキーを設定する必要があります。<br>APIキーは、[Google AI Studio](https://aistudio.google.com/api-keys)で作成できます。（無料枠のままで使用可能です）<br> - ウィンドウ左上の「File」→「Settings」をクリックします。<br> - 「Gemini」タブを選択し、APIキーを入力します。<br> - 「Save & Close」をクリックしてアプリを再起動します。
4. **食材の入力**: ウィンドウ左上のテキストボックスに、使用したい食材を入力し、「Add」ボタンをクリックします。<br>（食材は１つ入力するごとにAddボタンを押してください）
5. **メニューの提案**: 食材をすべて追加したら、「Suggest Recipe」ボタンをクリックします。<br>（処理が終わるまで数秒かかります）
6. **メニューの確認**: 提案されたメニューがウィンドウ右側に表示されます。
7. **メニューの保存**: 提案されたメニューの下にある「Save Recipe」ボタンをクリックすると、提案された日時ごとにメニューが保存されます。
8. **過去のメニューの確認**: ウィンドウ上部の「Managing」タブに切り替えると、過去に保存されたメニューを確認できます。<br>表の行をクリックすると、詳細が表示されます。

# トラブルシューティング
- **アプリが起動しない**: <br>JARファイルをタブルクリックしたときに、「A Java Exception has occurred」というエラーが表示される場合は、以下の手順に従ってください。
  1. 最新バージョンのJavaがインストールされていることを確認します。
  2. [こちら](https://johann.loefflmann.net/en/software/jarfix/index.html)から「jarfix.exe」をダウンロードし、実行します。これにより、JARファイルの関連付けが修正されます。
  3. 再度JARファイルをダブルクリックしてアプリを起動します。
- **「Suggest Recipe」ボタンを押すとエラーが発生する**: <br>APIキーが正しく設定されていることを確認してください。また、インターネット接続が安定していることも確認してください。
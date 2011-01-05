SDLoader

[はじめに]
SDLoaderは、シンプルなサーブレットコンテナです。
ServletAPI2.4準拠を目指していますが、大部分が未実装です。
ただし、サーブレット・JSPを動かす最低限の実装はしてあります。

以下の3つのJarを提供しています。

sdloader.jar・・・Servletのみ動作
sdloader-jsp12.jar・・・ServletとJSP1.2
sdloader-jsp20.jar・・・ServletとJSP2.0

[使い方]
jarファイルと同じ場所に、「webapps」フォルダを作成します。
webappsフォルダに、warファイル、ディレクトリ、コンテキストXMLのいずれかを配置します。

・warファイル
warファイルは、ファイル名がコンテキストパスになり、またwarファイル展開後の
フォルダがドキュメントルートになります。
例えばsample.warを配置し、中にindex.jspが入っているとすると、
http://localhost:30000/sample/index.jsp
でアクセスすることが出来ます。

・ディレクトリ
ディレクトリは、そのディレクトリがコンテキストパスになり、またドキュメントルートに
なります。
例えばsampleディレクトリを作成し、中にindex.jspが入っているとすると、
http://localhost:30000/sample/index.jsp
でアクセスすることが出来ます。

・コンテキストXML
<Context path="/example" docBase="../../example/webContents"/>
のような形で記述したXMLファイルを配置すると、そのファイルに従ってコンテキストパス
およびドキュメントルートを設定できます。
pathはコンテキストパスの設定で、docBaseがドキュメントルートになります。
path省略可能で、省略すると、このファイル名がコンテキストパスとなります。
docBaseは、.(ドット）表記からはじめるとwebappsフォルダからの相対パス、それ以外は絶対パスとなります。

[eclipseで利用する場合]
「example」というWebプロジェクトがあり、webContentsフォルダがドキュメントルートになっていると仮定します。
example（プロジェクトフォルダ）
  |--webContents
       |--WEB-INF
       |     |--web.xml
       |--test.jsp

このプロジェクトをSDLoaderで動かす場合、次のような設定を行います。

A、同一プロジェクトにSDLoaderを入れる場合
exampleプロジェクトに、sdloaderのjarを配置し、クラスパスに通します。
次に、exampleプロジェクト直下に、webappsフォルダを作成します。
フォルダに、「example.xml」を作成し、次のように記述します。
<Context path="/example" docBase="../webContents"/>
ファイル構成は次のようになります。
example（プロジェクトフォルダ）
  |--sdloader-jsp20.jar
  |--webapps
  |    |--example.xml
  |--webContents
       |--WEB-INF
       |     |--web.xml
       |--test.jsp

この状態で、sdloaderのJarの中の「sdloader.Open」クラスを右クリック実行もしくはデバック実行すると、
アプリケーションが動作します。
このやり方の場合プロジェクトが1つで済むため、リポジトリからもってきてすぐに実行できます。

B、SDLoader用のプロジェクトを作成する場合
「example」というWebプロジェクトがあり、webContentsフォルダがドキュメントルートになっていると仮定します。
example（プロジェクトフォルダ）
  |--webContents
       |--WEB-INF
       |     |--web.xml
       |--test.jsp

SDLoaderを実行させるために、SDLoader用のJavaプロジェクトを作成します。
SDLoaderプロジェクトに、sdloaderのjarを配置し、クラスパスに通します。
次にSDLoaderプロジェクト直下に、webappsフォルダを作成します。
フォルダに「example.xml」を作成し、次のように記述します。
<Context path="/example" docBase="../../example/webContents"/>
（webappsディレクトリから相対パスでexampleプロジェクトのドキュメントルートを指定する）
ファイル構成は次のようになります。

SDLoader（プロジェクトフォルダ）
  |--sdloader-jsp20.jar
  |--webapps
       |--example.xml  
example（プロジェクトフォルダ）
  |--webContents
       |--WEB-INF
       |     |--web.xml
       |--test.jsp

この状態で、sdloaderのJarの中の「sdloader.Open」クラスを右クリック実行もしくはデバック実行すると、
アプリケーションが動作します。
このやり方の場合、対象Webプロジェクトに影響を与えずにWebアプリを動作させることが出来ます。
















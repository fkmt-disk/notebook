notebook
========

MediaWiki、Textile、Scamlで書いたメモを書いてすぐブラウザで見れるHTTPサーバ。


#### 使い方

前提
	: Java、SBT、Gitがセットアップされていること。

以下のコマンド叩くとサーバ起動。
<pre>
$ git clone https://github.com/fkmt-disk/notebook.git
$ cd notebook
$ sbt run
</pre>

[localhost:9999](http://localhost:9999) にアクセス。

[localhost:9999/shutdown](http://localhost:9999/shutdown) でサーバ停止。


#### 概要

`./www`がドキュメントルート。

拡張子によって中身を判断。

* .wiki  
	=> MediaWiki
* .textile  
	=> Textile
* .scaml  
	=> Scaml
* .jade  
	=> Jade

それぞれ上記拡張子のファイルにアクセスすると、その中身をパース。  
`./www/assets/template.html`の中の「`{content}`」の部分に埋め込んでレスポンスを返す。


#### 別テンプレート埋め込み

<pre>
/{テンプレート種別}/&gt;&gt;&gt;

...いろいろ

&lt;&lt;&lt;
</pre>
という形式で、別テンプレートを埋め込むことができる。

例えば、textileにscamlを埋め込むには、`sample.textile`の中に
<pre>

(textileの記法でいろいろ...)

/scaml/&gt;&gt;&gt;
%div.alert.alert-danger
	メッセージ
&lt;&lt;&lt;

(textileの記法でいろいろ...)

</pre>
のように一部だけ別テンプレートの記法を使える。


#### 設定とか

`./conf/application.conf`で。

<pre>
notebook = {
  port = 9999
  docroot = "./www"
  backlog = 3
  threadpool = 3
  template = "/assets/template.html"
}
</pre>

* port  
	起動ポート
* docroot  
	ドキュメントルート
* backlog  
	リクエストを待機させておくキューの数
* threadpool  
	リクエストを捌くためのスレッドの数
* template  
	パースした後の埋め込み先HTML



## [Fake Module List](https://discord.gg/bVkYkfZyJZ)
- [5zig = trash.](https://discord.gg/bVkYkfZyJZ)

ArrayListのようなテキストを画面右上、または左上に表示するだけのModです。 <br />
カスタムフォントやアニメーションもなく、ただのテキストです <br />
使う理由があるとすれば、5zigが嫌いだったり ChromaHUDでちょっとづつ調整するのがだるかったりしたときとか


## `/fm help` から飛んだ人向け / Features
- prefix: `/fml | /fm | /fakemodules`

| \<arg1\> | 説明 | \<..args\> |
| :----: | :--: | :------: |
| toggle | 描画の切り替え | \<\> |
| size | 文字サイズの指定 | \<size:float\> |
| gap | 画面端との距離 | \<textGap(px):float\> |
| shadow | 文字の影の切り替え | \<\> |
| color | 未指定時の文字色 | \<color(hex):int16\> |
| open, folder, dir, config | 設定フォルダを開く | \<\> |
| help, readme, info, site | [ここ](https://github.com/luna724/fakemodulelist)に飛ぶ | \<\> |
| discord | [ここ](https://discord.gg/bVkYkfZyJZ)か[ここ](https://discord.gg/lunaclient)に飛ぶ | (bVkYkfZyJZ \| lunaclient) |
| update, rld, reload | FakeModuleList.jsonの再読み込み | \<\> |
| new | FakeModuleList.jsonの作成 | \<\> |
| 5zig | `throw IllegalArgumentException("5zig = trash.")` | \<\> |


## モジュール追加方法
1. `/fml new` でファイルを生成
2. `/fml open` でディレクトリを開く
3. `fakeModuleList.json` をエディタで開く
4. 以下の規則のjsonを書く

```json
{
  "なんでもいいけどかぶっちゃダメ": ["モジュール名", "モード(省略可)"],
	"a": ["Module", "Mode"],
	"b": ["RageQuit", "Manual"],
	"c": ["PlayerControll", "Keyboard"],
	"d": ["FakeModule"]
}
```


#### [基本githubに落ちてるベータ版を配布する場所](https://discord.gg/bVkYkfZyJZ)

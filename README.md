# MasterThesisJiaming

## Data Description
The dataset is from TREC 2017 PM track, with 889 xml files and a collection of extra topics in the txt format.

## Preparation
There are some duplicate 

1. Loading File Paths: __GetFilePath__
2. Parsing XML: __XMLParser__
 * A method returns a <ID, Abstract> map
 * A method returns a <ID, Title> map
3. Create Index: __CreateIndexWithTitle__ 
4. Search: __BM25Retrieval__
 * The difference is whether to parse the query with titles

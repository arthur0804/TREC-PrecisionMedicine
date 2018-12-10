# MasterThesisJiaming

## Data Description
The dataset is from TREC 2017 PM track, with 889 xml files and a collection of extra topics in the txt format.

## Preparation
There are some duplicate documents with the same ID in the collection. The only difference is the publishing date (the changes in abstract contents are small enought to be ignored). So we just use the abstract content at the first hit. If we do not pay attention to these duplicate document IDs, the retrieved result could contain these duplicates and make the evaluation script unable to run.

### Get Duplicate Document ID Collection

1. Loading File Paths: __GetFilePath__
2. Parsing XML: __XMLParser__
 * A method returns a <ID, Abstract> map
 * A method returns a <ID, Title> map
3. Create Index: __CreateIndexWithTitle__ 
4. Search: __BM25Retrieval__
 * The difference is whether to parse the query with titles

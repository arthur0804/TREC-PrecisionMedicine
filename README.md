# MasterThesisJiaming

## Data Description
The dataset is from TREC 2017 PM track, with 889 xml files and a collection of extra topics in the txt format.

## Preparation
There are some duplicate documents with the same ID in the collection. The only difference is the publishing date (the changes in abstract contents are small enought to be ignored). So we just use the abstract content at the first hit. If we do not pay attention to these duplicate document IDs, the retrieved result could contain these duplicates and make the evaluation script unable to run.

### Get Duplicate Document ID Collection
1. Call _GetDuplicateDocumentID.GetAllDocumentIDCollection()_ to get all the document IDs.
2. Call _GetDuplicateDocumentID.GetDuplicateDocumentIDs()_ to get a file with all the duplicate document IDS, i.e. these IDs appear more than once in the collection.

## Indexing
### Loading File Paths
Call _GetFilePath.GetFilePaths(url)_ to get an ArrayList of the file paths.

### Parse XML
1. Call _XMLParser.ReadIDAndAbstract(url)_ to get a <ID, Abstract> map.
2. Call _XMLParser.ReadIDAndTitle(url)_ to get a a <ID, Title> map.

### Create Index
Call _CreateIndexWithTitle_ to create indexes.
1. Import the duplicate ID collection while indexing to make sure these duplicate documents are indexed only once.
2. Set the similarity at this stage.
3. Set parameters for each filed, e.g. whether stored, tokenized, index options and etc.

### Extra Topics
After checking all the extra topics files, it was found that all the first lines are meeting info. So we just skip the first line in parsing files.
Simply read titles (remember to remove "Title:" for each title) and abstracts.
Index these files.

## Query
Create an ArrayList of queries.
Call _BM25Retrieval.SearchMethod_ to run the queries.
Be sure to set correct clause parameters.

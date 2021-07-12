from dremio_client import init
client = init() # initialise connectivity to Dremio via config file
catalog = client.data # fetch catalog

# vds = catalog.space.vds.get() # fetch a specific dataset
# df = vds.query() # query the first 1000 rows of the dataset and return as a DataFrame
# pds = catalog.source.pds.get() # fetch a physical dataset
# pds.metadata_refresh() # refresh metadata on that dataset
print("[INFO] im ok")

q = """
SELECT * FROM s3."ydmoneytime"."or_data"."fiff_db_example"."nor_json"."pext_20-02-27_10-19-41_Bella_DATACO-354_36001_47999.FEIF_Master-state.docs.framedata.jsonl" WHERE x > 0 LIMIT 10
"""

q = """
'SELECT * FROM mes3."ydmoneytime"."or_data"."fiff_db_example"."nor_json"."pext_20-02-27_10-19-41_Bella_DATACO-354_36001_47999.FEIF_Wheels-meas.docs.framedata.jsonl" WHERE x > 0 LIMIT 10'
"""

q2 ="""
SELECT T1.T0grabIdx, T1.id, T1.x, T2.p
FROM s3."ydmoneytime"."or_data"."fiff_db_example"."nor_json"."pext_20-02-27_10-19-41_Bella_DATACO-354_36001_47999.FEIF_Master-state.docs.framedata.jsonl"  T1
JOIN s3."ydmoneytime"."or_data"."fiff_db_example"."nor_json"."pext_20-02-27_10-19-41_Bella_DATACO-354_36001_47999.FEIF_Wheels-state.docs.framedata.jsonl"  T2
ON T1.T0grabIdx = T2.T0grabIdx and T1.id = T2.id
WHERE T1.x > 0
LIMIT 10
"""

from pprint import pprint
res = client.query(q2)
# pprint(res)
pprint(res)
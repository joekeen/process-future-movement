# process-future-movement

# API
##POST
/api/v1/upload

##GET
/api/v1/job/{id}
{ jobId: 1, jobStatus: "COMPLETED", message: null }

/api/v1/summary/{jobId}
```
[ 
    {"clientInformation":"CL123400020001","productInformation":"SGXFUNK20100910","totalTransactionAmount":"-52"},
    {"clientInformation":"CL123400030001","productInformation":"CMEFUN120100910","totalTransactionAmount":"285"},
    {"clientInformation":"CL123400030001","productInformation":"CMEFUNK.20100910","totalTransactionAmount":"-215"},
    {"clientInformation":"CL432100020001","productInformation":"SGXFUNK20100910","totalTransactionAmount":"46"},
    {"clientInformation":"CL432100030001","productInformation":"CMEFUN120100910","totalTransactionAmount":"-79"}
]
```

/api/v1/summary/{jobId}?format=csv
```
Client_Information,Product_Information,Total_Transaction_Amount
CL123400020001,SGXFUNK20100910,-52
CL123400030001,CMEFUN120100910,285
CL123400030001,CMEFUNK.20100910,-215
CL432100020001,SGXFUNK20100910,46
CL432100030001,CMEFUN120100910,-79
```
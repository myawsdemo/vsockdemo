#!/bin/bash
FILE=vsockserver.eif
if [ -f "$FILE" ]; then
    rm $FILE
fi

RunningEnclave=$(nitro-cli describe-enclaves | jq -r ".[0].EnclaveID")
if [ -n "$RunningEnclave" ]; then
	nitro-cli terminate-enclave --enclave-id $(nitro-cli describe-enclaves | jq -r ".[0].EnclaveID");
fi

#docker rmi -f $(docker images -a -q)
docker rmi vsockserver:latest

docker build -t vsockserver:latest .
nitro-cli build-enclave --docker-uri vsockserver:latest  --output-file vsockserver.eif > EnclaveImage.log
nitro-cli run-enclave --cpu-count 4 --memory 4096 --enclave-cid 5 --eif-path vsockserver.eif --debug-mode
# nitro-cli console --enclave-id $(nitro-cli describe-enclaves | jq -r ".[0].EnclaveID")
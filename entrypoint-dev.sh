#!/bin/bash
echo "RUNNING DEVELOPMENT SERVER VIA LEININGEN"

lein repl :headless :host 0.0.0.0 :port 17020

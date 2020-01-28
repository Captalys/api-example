# api-example

How Captalys is working in Clojure to build Services APIs to its internal processes.


## Usage

```bash
WEBSERVER_PORT=3000 CURRENT_UID=$(id -u):$(id -g) docker-compose up
```

Connect your project to the new created REPL. If you are
using CIDER in Emacs, just `M-x cider-connect-clj` or `C-c
M-c` keychord. The host is `localhost` and the port should
be `17020`.

Now, start the webserver with the `(start)` command inside the REPL.
Your API should be on at the `http://localhost:17021/`

You should also manually apply the migrations. This should do the trick:

```clj
(require '[api-example.migrations :as m])
(m/apply-migrations)
```

## Emacs users

Please, edit the file `.dir-locals.el` to match the path of
your machine until this project. The CIDER package needs
that to provide you with auto-completions and more features
when you connect to a remote REPL [remote in this case
because we are outside Docker connecting to a REPL inside of
it.]

## License

Copyright © 2020 Wanderson Ferreira

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.

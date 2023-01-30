### 1 INSTALL PYTHON
   https://www.python.org/downloads/

### 2 INSTALL PIP

https://pip.pypa.io/en/stable/installation/

### 3 INSTALL PRE COMMIT

https://pre-commit.com/index.html


```bash
pip install pre-commit
```

1. CHECK

    ```bash
    pre-commit --version
    ```

2. Install the git hook scripts

    ```bash
    pre-commit install
    ```

3. (optional) Run against all the files

    ```bash
    pre-commit run --all-files
    ```

### 4 (IF NECESSARY) INSTALL DOCKER

[Docker for Ubuntu](https://docs.docker.com/engine/install/ubuntu/)

[Docker for Windows](https://docs.docker.com/desktop/install/windows-install/)

1. HOW TO INSTALL DOCKER ON UBUNTU

    ```bash
    sudo apt-get remove docker docker-engine docker.io containerd runc
    ```

    ```bash
    sudo apt-get update
    sudo apt-get install \
        ca-certificates \
        curl \
        gnupg \
        lsb-release
    ```

    ```bash
    sudo mkdir -p /etc/apt/keyrings
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
    ```

    ```bash
    echo \
    "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
    $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
    ```

    ```bash
    sudo apt-get update
    ```

    ```bash
    sudo apt-get install docker-ce docker-ce-cli containerd.io docker-compose-plugin
    ```

    ```bash
    sudo docker run hello-world
    ```

## TRYING TO RUN

```bash
pre-commit run --all-files
```

**GOT ERROR:**

```bash
An unexpected error has occurred: CalledProcessError: command: ('/usr/bin/docker', 'build', '--tag', 'pre-commit-21ee919ec9ff83bd359013f402d97db8', '--label', 'PRE_COMMIT', '--pull', '.')
return code: 1
stdout: (none)
stderr:
    Got permission denied while trying to connect to the Docker daemon socket at unix:///var/run/docker.sock: Post "http://%2Fvar%2Frun%2Fdocker.sock/v1.24/build?buildargs=%7B%7D&cachefrom=%5B%5D&cgroupparent=&cpuperiod=0&cpuquota=0&cpusetcpus=&cpusetmems=&cpushares=0&dockerfile=Dockerfile&labels=%7B%22PRE_COMMIT%22%3A%22%22%7D&memory=0&memswap=0&networkmode=default&pull=1&rm=1&shmsize=0&t=pre-commit-21ee919ec9ff83bd359013f402d97db8&target=&ulimits=null&version=1": dial unix /var/run/docker.sock: connect: permission denied

Check the log at /home/maria/.cache/pre-commit/pre-commit.log
```

**SOLUTION UBUNTU**

```bash
sudo chmod 666 /var/run/docker.sock
```

https://www.digitalocean.com/community/questions/how-to-fix-docker-got-permission-denied-while-trying-to-connect-to-the-docker-daemon-socket



### READ MORE

[Google Java Format](https://github.com/google/google-java-format/wiki/FAQ)

[Google Java Format 2](https://github.com/marketplace/actions/google-java-format)

### Testing other hooks

`.pre-commit-config.yaml`

```yaml
repos:
-   repo: https://github.com/pre-commit/pre-commit-hooks
    rev: v2.3.0
    hooks:
    -   id: check-yaml
    -   id: end-of-file-fixer
    -   id: trailing-whitespace
# -   repo: https://github.com/gherynos/pre-commit-java
#     rev: v0.2.0
#     hooks:
#     - id: pmd
#       exclude: /test/
#     - id: cpd
#       exclude: /test/
#     - id: checkstyle
#       exclude: /test/
#     -   id: checkstyle
-   repo: https://github.com/macisamuele/language-formatters-pre-commit-hooks
    rev: v1.4.2
    hooks:
    - id: pretty-format-java
      args: [--autofix]
-   repo: https://github.com/comkieffer/pre-commit-xmllint.git
    rev: 1.0.0
    hooks:
    - id: xmllint
#-   repo: https://github.com/ejba/pre-commit-maven
#    rev: v0.3.3
#    hooks:
#     -   id: maven
#         args: ['clean compile verify']
#     -   id: maven-test
#     -   id: maven-checkstyle
```

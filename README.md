# Battleship Game

![alt text](https://www.ingenieur.de/wp-content/uploads/2017/11/2017/18212_Auslaufen-des-Flugzeugtraegers-USS-Gerald-R.-Ford-e1512744621109.jpg)

![RepoSize](https://img.shields.io/github/repo-size/julianboehne/battleship-game)
![Forks](https://img.shields.io/github/forks/julianboehne/battleship-game?color=green&style=social)
[![codecov](https://codecov.io/gh/julianboehne/battleship-game/branch/main/graph/badge.svg?token=P26ZUHIDD4)](https://codecov.io/gh/julianboehne/battleship-game)
![Scala CI](https://github.com/julianboehne/battleship-game/actions/workflows/scala.yml/badge.svg)
---

## Contributors

[Julian Böhne](https://github.com/julianboehne) | [Fabian Meyer](https://github.com/Nerbry72)


## Description
This is a implementation of the battleship-game. 2 Player can play against each other in a 10x10 battleship field.

## How to Play

### Enter your name

If you don't enter a name you will get an automatic generated player-name

<img src="src/resources/player_Create_State.png" style="width:600px;"/>

### Enter your ships

You can place four different sized ships.

You also have the possibility to auto-place your ships in the menu-bar.

<img src="src/resources/Ship_State.png" style="width:600px;"/>

### Play and enjoy

Now you can start playing battleship-game.

<img src="src/resources/Shot_State.png" style="width:600px;"/>


# Init with docker

# Init with kubernetes

You can use kubernetes locally with dthe docker-k8s extensions or with minikube. 

There are two ways to deploy the application with k8s.

- default deployment with kubctl
- using argocd as interactive UI

### Default Setup

```
kubectl apply -f k8s-persistence.yaml
kubectl apply -f k8s-battleship-base.yaml
```

###  ArgoCD Setup

You can deploy ArgoCD to your cluster. After deploying the configuration ArgoCD will pull the config-files from Github by itsself.

```
# deploy argocd
kubectl create namespace argocd
kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml

# Port forwarding to ArgoCD
kubectl port-forward svc/argocd-server -n argocd 8080:443

# Get login credentials
kubectl -n argocd get secret argocd-initial-admin-secret -o jsonpath="{.data.password}"
# decode passwort with base64
```

You can now go to ArgoCD and integrate both files!
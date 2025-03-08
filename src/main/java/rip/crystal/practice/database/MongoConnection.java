package rip.crystal.practice.database;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import rip.crystal.practice.cPractice; // インポートを追加
import rip.crystal.practice.utilities.chat.CC;
import org.bukkit.Bukkit; // Bukkit をインポート

import java.util.Objects;

@Getter
public class MongoConnection {

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    public MongoConnection(String uri) {
        try {
            System.out.println(CC.translate("&a[cPractice] Connecting to MongoDB using URI..."));
            ConnectionString connectionString = new ConnectionString(uri);
            MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString).build();

            this.mongoClient = MongoClients.create(settings);
            this.mongoDatabase = this.mongoClient.getDatabase(Objects.requireNonNull(connectionString.getDatabase()));
            System.out.println(CC.translate("&a[cPractice] Successfully connected to MongoDB using URI."));
        } catch (Exception e) {
            System.out.println(CC.translate("&c[cPractice] Failed to connect to MongoDB using URI!"));
            e.printStackTrace();
            Bukkit.getServer().getPluginManager().disablePlugin(cPractice.get()); // プラグイン停止処理を追加
        }
    }

    public MongoConnection(String host, int port, String database) {
        try {
            System.out.println(CC.translate("&a[cPractice] Connecting to MongoDB using Host, Port, Database..."));
            this.mongoDatabase = new com.mongodb.MongoClient(host, port).getDatabase(database);
            System.out.println(CC.translate("&a[cPractice] Successfully connected to MongoDB using Host, Port, Database."));
        } catch (Exception e) {
            System.out.println(CC.translate("&c[cPractice] Failed to connect to MongoDB using Host, Port, Database!"));
            e.printStackTrace();
            Bukkit.getServer().getPluginManager().disablePlugin(cPractice.get()); // プラグイン停止処理を追加
        }
    }

    public MongoConnection(String host, int port, String username, String password, String database) {
        try {
            System.out.println(CC.translate("&a[cPractice] Connecting to MongoDB using Host, Port, Username, Password, Database..."));
            this.mongoDatabase = new com.mongodb.MongoClient(
                new ServerAddress(host, port),
                MongoCredential.createCredential(username, database, password.toCharArray()),
                MongoClientOptions.builder().build()).getDatabase(database);
            System.out.println(CC.translate("&a[cPractice] Successfully connected to MongoDB using Host, Port, Username, Password, Database."));
        } catch (Exception e) {
            System.out.println(CC.translate("&c[cPractice] Failed to connect to MongoDB using Host, Port, Username, Password, Database!"));
            e.printStackTrace();
            Bukkit.getServer().getPluginManager().disablePlugin(cPractice.get()); // プラグイン停止処理を追加
        }
    }
}
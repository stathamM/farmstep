package com.farmstep.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.farmstep.dto.DiagnosisRequest;
import com.farmstep.dto.DiagnosisResponse;
import com.farmstep.dto.RegionDto;
import com.farmstep.dto.ResourceLinkDto;
import com.farmstep.entity.DiagnosisResultEntity;
import com.farmstep.repository.DiagnosisResultRepository;

@Service
public class DiagnosisService {

    private final DiagnosisResultRepository diagnosisResultRepository;
    private final ResourceService resourceService;
    private final AiService aiService;
    private final TaskService taskService;

    public DiagnosisService(
            DiagnosisResultRepository diagnosisResultRepository,
            ResourceService resourceService,
            AiService aiService,
            TaskService taskService
    ) {
        this.diagnosisResultRepository = diagnosisResultRepository;
        this.resourceService = resourceService;
        this.aiService = aiService;
        this.taskService = taskService;
    }

    public DiagnosisResponse diagnose(DiagnosisRequest request) {

        String experience = request.getExperience();
        String migration = request.getMigration();
        int budget = request.getBudget();
        String stability = request.getStability();
        String independence = request.getIndependence();

        String type;
        String nextAction;
        String funding;
        String recommendedStyle;
        String caution;
        String regionHint;

        // 1. 研修型
        if ("none".equals(experience) && budget == 0) {
            type = "研修型";

            nextAction = """
                    STEP1 農業体験を探す
                    STEP2 就農相談会に参加する
                    STEP3 研修制度を比較する
                    """;

            funding = """
                    必要資金：0〜50万円
                    利用できる支援例：就農準備資金（年間150万円）
                    """;

            recommendedStyle = """
                    ・農業体験から始める
                    ・研修制度を利用する
                    ・雇用就農も比較する
                    """;

            caution = """
                    ・いきなり設備投資しない
                    ・農地取得を急がない
                    ・まずは現場経験を積む
                    """;

            regionHint = """
                    ・研修制度が整っている地域
                    ・新規就農者の受け入れが多い自治体
                    ・移住支援が手厚い地域
                    """;
        }

        // 2. 移住就農型
        else if (("yes".equals(migration) || "maybe".equals(migration))
                && "yes".equals(independence)) {

            type = "移住就農型";

            nextAction = """
                    STEP1 移住支援のある自治体を探す
                    STEP2 就農支援制度を比較する
                    STEP3 現地見学に行く
                    """;

            funding = """
                    必要資金：100〜300万円
                    利用できる支援例：移住支援金＋就農支援制度
                    """;

            recommendedStyle = """
                    ・移住支援と就農支援をセットで見る
                    ・地域との相性を重視する
                    ・住居支援も確認する
                    """;

            caution = """
                    ・地域だけで決めない
                    ・現地見学なしで決めない
                    ・支援制度の条件を確認する
                    """;

            regionHint = """
                    ・移住支援が強い地域
                    ・空き家支援がある自治体
                    ・新規就農者窓口がある地域
                    """;
        }

        // 3. 雇用就農型
        else if ("high".equals(stability) && "no".equals(independence)) {

            type = "雇用就農型";

            nextAction = """
                    STEP1 農業法人の求人を探す
                    STEP2 条件を比較する
                    STEP3 農業法人を見学する
                    """;

            funding = """
                    必要資金：0〜100万円
                    安定収入を得ながら農業経験を積めます
                    """;

            recommendedStyle = """
                    ・農業法人で働きながら学ぶ
                    ・収入を確保しながら経験を積む
                    ・将来独立する選択肢も残す
                    """;

            caution = """
                    ・給与だけで決めない
                    ・作物や働き方も確認する
                    ・将来のキャリアも考える
                    """;

            regionHint = """
                    ・農業法人が多い地域
                    ・雇用就農の実績がある地域
                    ・農業集積地
                    """;
        }

        // 4. 独立準備型
        else {

            type = "独立準備型";

            nextAction = """
                    STEP1 作物候補を絞る
                    STEP2 資金計画を立てる
                    STEP3 就農候補地を比較する
                    """;

            funding = """
                    必要資金：300〜500万円
                    利用できる支援例：経営開始資金（最大450万円）
                    """;

            recommendedStyle = """
                    ・作物選びを先に進める
                    ・資金と販路を同時に考える
                    ・独立前に現場経験を積む
                    """;

            caution = """
                    ・自己資金を使いすぎない
                    ・設備投資を一気にしない
                    ・販路を後回しにしない
                    """;

            regionHint = """
                    ・作物に適した地域
                    ・販路がある地域
                    ・新規就農支援が整っている自治体
                    """;
        }

        // AIコメント生成
        String aiMessage;
        try {
            aiMessage = aiService.generateDiagnosisComment(
                    type,
                    experience,
                    migration,
                    budget,
                    stability,
                    independence
            );
        } catch (Exception e) {
            aiMessage = "農業を始める際は、まず小さく始めて経験を積むことが大切です。地域の就農相談窓口に相談してみましょう。";
        }

        // 診断タイプに応じた関連リンク取得
        List<ResourceLinkDto> resources = resourceService.findResourcesByDiagnosisType(type);

        // 診断タイプに応じた地域取得
        List<RegionDto> regions = resourceService.findRegionsByDiagnosisType(type);
        
     // 診断結果保存
        DiagnosisResultEntity saved = saveDiagnosisResult(request, type);

        if (request.getUserId() != null) {
            taskService.createInitialTasks(
                    request.getUserId(),
                    saved.getId(),
                    type
            );
        }

        DiagnosisResponse response = new DiagnosisResponse(
                saved.getId(),
                type,
                aiMessage,
                nextAction,
                funding,
                recommendedStyle,
                caution,
                regionHint,
                resources,
                regions
        );

        

        return response;
    }

    private DiagnosisResultEntity saveDiagnosisResult(DiagnosisRequest request, String diagnosisType) {
        DiagnosisResultEntity entity = new DiagnosisResultEntity();
        entity.setExperience(request.getExperience());
        entity.setMigration(request.getMigration());
        entity.setBudget(request.getBudget());
        entity.setStability(request.getStability());
        entity.setIndependence(request.getIndependence());
        entity.setDiagnosisType(diagnosisType);

        return diagnosisResultRepository.save(entity);
    }
}